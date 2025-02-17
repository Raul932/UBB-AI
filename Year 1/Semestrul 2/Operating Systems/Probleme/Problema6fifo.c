#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <ctype.h>
#include <dirent.h>

#define FIFO_TO_CHILD "/tmp/fifo_to_child"
#define FIFO_FROM_CHILD "/tmp/fifo_from_child"

int is_text_file(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) return 0;

    unsigned char buf[512];
    size_t bytes_read = fread(buf, sizeof(char), sizeof(buf), file);
    fclose(file);

    if (bytes_read == 0) {
        return 1;
    }

    size_t text_chars = 0;
    for (size_t i = 0; i < bytes_read; ++i) {
        if (isprint(buf[i]) || isspace(buf[i])) {
            text_chars++;
        }
    }

    return (text_chars > 0.95 * bytes_read);
}

int main() {
    mkfifo(FIFO_TO_CHILD, 0666);
    mkfifo(FIFO_FROM_CHILD, 0666);

    pid_t pid = fork();
    if (pid == -1) {
        perror("fork");
        return 1;
    }

    if (pid == 0) {
        int fd_in = open(FIFO_TO_CHILD, O_RDONLY);
        int fd_out = open(FIFO_FROM_CHILD, O_WRONLY);

        char dir_name[256] = {0};
        read(fd_in, dir_name, sizeof(dir_name) - 1);
        dir_name[strcspn(dir_name, "\n")] = 0;

        DIR *dir = opendir(dir_name);
        if (!dir) {
            perror("opendir");
            char *error_msg = "Directory does not exist\n";
            write(fd_out, error_msg, strlen(error_msg));
        } else {
            struct dirent *entry;
            while ((entry = readdir(dir)) != NULL) {
                if (entry->d_type == DT_REG) {
                    char path[1024];
                    sprintf(path, "%s/%s", dir_name, entry->d_name);
                    if (is_text_file(path)) {
                        write(fd_out, entry->d_name, strlen(entry->d_name));
                        write(fd_out, "\n", 1);
                    }
                }
            }
            closedir(dir);
        }
        close(fd_in);
        close(fd_out);
        exit(0);
    } else {
        int fd_out = open(FIFO_TO_CHILD, O_WRONLY);
        int fd_in = open(FIFO_FROM_CHILD, O_RDONLY);

        printf("Enter the directory name: ");
        char dir_name[256];
        fgets(dir_name, sizeof(dir_name), stdin);
        dir_name[strcspn(dir_name, "\n")] = 0;

        write(fd_out, dir_name, strlen(dir_name));

        char buffer[1024] = {0};
        int bytes_read = read(fd_in, buffer, sizeof(buffer) - 1);
        if (bytes_read > 0) {
            buffer[bytes_read] = '\0';
            printf("Received from child:\n%s", buffer);
        }

        close(fd_out);
        close(fd_in);
    }

    unlink(FIFO_TO_CHILD);
    unlink(FIFO_FROM_CHILD);
    return 0;
}
