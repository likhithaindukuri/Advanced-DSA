#include <iostream>
#include <string>
using namespace std;
struct Student
{
    int id;
    string name;
    int score;
    int time;
    int modules;
};
bool comesBefore(Student a, Student b)
{
    if (a.score != b.score)
        return a.score > b.score;

    return a.time < b.time;
}
void merge(Student a[], int low, int mid, int high, long long &comparisons)
{
    int n1 = mid - low + 1;
    int n2 = high - mid;
    Student *left = new Student[n1];
    Student *right = new Student[n2];
    for (int i = 0; i < n1; i++)
        left[i] = a[low + i];
    for (int i = 0; i < n2; i++)
        right[i] = a[mid + 1 + i];
    int i = 0, j = 0, k = low;
    while (i < n1 && j < n2)
    {
        comparisons++;
        if (comesBefore(left[i], right[j]))
        {
            a[k] = left[i];
            i++;
        }
        else
        {
            a[k] = right[j];
            j++;
        }
        k++;
    }
    while (i < n1)
    {
        a[k] = left[i];
        i++;
        k++;
    }
    while (j < n2)
    {
        a[k] = right[j];
        j++;
        k++;
    }
    delete[] left;
    delete[] right;
}
void mergeSort(Student a[], int low, int high, long long &comparisons)
{
    if (low < high)
    {
        int mid = (low + high) / 2;
        mergeSort(a, low, mid, comparisons);
        mergeSort(a, mid + 1, high, comparisons);
        merge(a, low, mid, high, comparisons);
    }
}
int binarySearch(Student a[], int n, int target, int &comparisons)
{
    int low = 0;
    int high = n - 1;
    while (low <= high)
    {
        int mid = (low + high) / 2;
        comparisons++;
        if (a[mid].score == target)
            return mid;
        if (a[mid].score < target)
            high = mid - 1;
        else
            low = mid + 1;
    }
    return -1;
}
int firstOccurrence(Student a[], int n, int target)
{
    int low = 0;
    int high = n - 1;
    int answer = -1;
    while (low <= high)
    {
        int mid = (low + high) / 2;
        if (a[mid].score == target)
        {
            answer = mid;
            high = mid - 1;
        }
        else if (a[mid].score < target)
        {
            high = mid - 1;
        }
        else
        {
            low = mid + 1;
        }
    }
    return answer;
}
int lastOccurrence(Student a[], int n, int target)
{
    int low = 0;
    int high = n - 1;
    int answer = -1;
    while (low <= high)
    {
        int mid = (low + high) / 2;

        if (a[mid].score == target)
        {
            answer = mid;
            low = mid + 1;
        }
        else if (a[mid].score < target)
        {
            high = mid - 1;
        }
        else
        {
            low = mid + 1;
        }
    }
    return answer;
}
void displayStudent(Student s, int rank)
{
    cout << rank << " "
         << s.id << " "
         << s.name << " "
         << s.score << " "
         << s.time << " "
         << s.modules << endl;
}
int main()
{
    int n;
    cout << "Enter number of students: ";
    cin >> n;
    Student *a = new Student[n];
    cout << "Enter Student_ID Name Score CompletionTime Modules:\n";
    for (int i = 0; i < n; i++)
    {
        cin >> a[i].id >> a[i].name >> a[i].score >> a[i].time >> a[i].modules;
    }
    long long sortComparisons = 0;
    mergeSort(a, 0, n - 1, sortComparisons);
    cout << "\nSorted Student Records:\n";
    cout << "Rank ID Name Score Time Modules\n";
    for (int i = 0; i < n; i++)
    {
        displayStudent(a[i], i + 1);
    }
    int target;
    cout << "\nEnter target Course Score: ";
    cin >> target;
    int searchComparisons = 0;
    int position = binarySearch(a, n, target, searchComparisons);
    if (position != -1)
    {
        int first = firstOccurrence(a, n, target);
        int last = lastOccurrence(a, n, target);
        cout << "\nTarget score found.\n";
        cout << "First occurrence: " << first << endl;
        cout << "Last occurrence: " << last << endl;
        cout << "\nStudents with score " << target << ":\n";
        cout << "Rank ID Name Score Time Modules\n";
        for (int i = first; i <= last; i++)
        {
            displayStudent(a[i], i + 1);
        }
    }
    else
    {
        int higher = -1;
        int lower = -1;
        for (int i = 0; i < n; i++)
        {
            if (a[i].score > target)
            {
                higher = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; i--)
        {
            if (a[i].score < target)
            {
                lower = i;
                break;
            }
        }
        cout << "\nTarget score not found.\n";
        if (higher != -1)
            cout << "Closest higher score: "
                 << a[higher].score << endl;
        else
            cout << "No higher score available.\n";

        if (lower != -1)
            cout << "Closest lower score: "
                 << a[lower].score << endl;
        else
            cout << "No lower score available.\n";
    }
    cout << "\nSearch Result:\n";
    if (position != -1)
    {
        cout << "Target Score: " << target << endl;
        cout << "Rank of first student: "
             << firstOccurrence(a, n, target) + 1 << endl;
        cout << "Number of Binary Search comparisons: "
             << searchComparisons << endl;
    }
    else
    {
        cout << "Target Score: " << target << endl;
        cout << "Rank: Not available" << endl;
        cout << "Number of Binary Search comparisons: "
             << searchComparisons << endl;
    }
    cout << "\nNumber of Merge Sort comparisons: "
         << sortComparisons << endl;

    return 0;
}