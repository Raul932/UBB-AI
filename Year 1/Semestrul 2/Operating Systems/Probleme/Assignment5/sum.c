#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int divide_and_conquer_sum(int arr[], int left, int right) {
    if (left == right) {
        return arr[left];
    }

    int mid = left + (right - left) / 2;

    int fd1[2], fd2[2];
    pipe(fd1);
    pipe(fd2);

    pid_t pid1 = fork();
    if (pid1 == 0) {
        close(fd1[0]);
        int left_sum = divide_and_conquer_sum(arr, left, mid);
        write(fd1[1], &left_sum, sizeof(left_sum));
        close(fd1[1]);
        exit(0);
    }

    pid_t pid2 = fork();
    if (pid2 == 0) {
        close(fd2[0]);
        int right_sum = divide_and_conquer_sum(arr, mid + 1, right);
        write(fd2[1], &right_sum, sizeof(right_sum));
        close(fd2[1]);
        exit(0);
    }

    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);

    int left_sum, right_sum;
    close(fd1[1]);
    close(fd2[1]);
    read(fd1[0], &left_sum, sizeof(left_sum));

    read(fd2[0], &right_sum, sizeof(right_sum));
    close(fd1[0]);
    close(fd2[0]);

    return left_sum + right_sum;
}

int main() {
    int n;

    printf("Enter the number of elements in the array: ");
    scanf("%d", &n);

    int *arr = (int *)malloc(n * sizeof(int));
    if (arr == NULL) {
        perror("Failed to allocate memory");
        return EXIT_FAILURE;
    }

    printf("Enter the elements of the array: ");
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    int total_sum = divide_and_conquer_sum(arr, 0, n - 1);

    printf("The sum of the array is: %d\n", total_sum);

    free(arr);

    return 0;
}
