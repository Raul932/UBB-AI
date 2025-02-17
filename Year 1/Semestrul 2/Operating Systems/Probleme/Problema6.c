#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <ctype.h>
int is_text_file(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) return 0;
    unsigned char buf[512];
    size_t bytes_read = fread(buf, sizeof(char), sizeof(buf), file);
    fclose(file);

    if (bytes_read == 0) {
        return 0;
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
    int fd[2], fd2[2];
    if (pipe(fd) == -1 || pipe(fd2) == -1) {
        perror("pipe");
        return 1;
    }

    pid_t pid = fork();
    if (pid == -1) {
        perror("fork");
        return 1;
    }

    if (pid == 0) {
        close(fd[1]);
        close(fd2[0]);

        char dir_name[256] = {0};
        int num_read = read(fd[0], dir_name, sizeof(dir_name) - 1);
        if (num_read == -1) {
            perror("read");
            exit(1);
        }

        dir_name[num_read] = '\0';

        DIR *dir = opendir(dir_name);
        if (!dir) {
            perror("opendir");
            char *error_msg = "Directory does not exist\n";
            write(fd2[1], error_msg, strlen(error_msg));
        } else {
            struct dirent *entry;
            while ((entry = readdir(dir)) != NULL) {
                if (entry->d_type == DT_REG) {
                    char path[1024];
                    sprintf(path, "%s/%s", dir_name, entry->d_name);
                    if (is_text_file(path)) {
                        write(fd2[1], entry->d_name, strlen(entry->d_name));
                        write(fd2[1], "\n", 1);
                    }
                }
            }
            closedir(dir);
        }
        close(fd[0]);
        close(fd2[1]);
        exit(0);
    } else {
        close(fd[0]);
        close(fd2[1]);

        char dir_name[256];
        printf("Enter the directory name: ");
        fgets(dir_name, sizeof(dir_name), stdin);
        dir_name[strcspn(dir_name, "\n")] = 0;

        write(fd[1], dir_name, strlen(dir_name));

        wait(NULL);

        char buffer[1024] = {0};
        int n = read(fd2[0], buffer, sizeof(buffer) - 1);
        if (n > 0) {
            buffer[n] = '\0';
            printf("Parent: Received from child:\n%s", buffer);
        }

        close(fd[1]);
        close(fd2[0]);
    }

    return 0;
}
