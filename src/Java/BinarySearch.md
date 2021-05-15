# Binary Search
Binary Search is a type of search algorithm, which also known as **Half-Interval Search** or **Logarithmic Search**  
Real-life Example:  
> Finding a word in hard-cover dictionary.  
> Finding a certain contact in phone book.  
>   
> *Binary Search is efficient alogrithm for finding an item from a sorted list of items.*

### Algorithm
*Condition*:  
Given an array A of n elements with values A_0,A_1,A_2,…,A_(n-1) sorted such that A_0  ≤ A_1  ≤ A_2≤⋯ ≤ A_(n-1),  
and target value T, the following subroutine uses binary search to find the index of T in A.  
  
**Subroutine**  
> ``` Java
> public static int BinarySearch(int[] array, int lo, int hi, int key) {
> ...
> }
1. Set L to 0 and R to n-1  
> ``` Java
> int lo = 0;
> int hi = array.length - 1;
2. if L > R, the search terminates as unsuccessful.
> ``` Java
> if (lo > hi) {
>   // Return negative integer, which inplies unsuccessful search result
>   return -1;
> }
3. Set m (index of middle element) to the floor of (L+R)/2, which is the greatest integer less than or equal to (L+R)/2.  
> ``` Java
> int mid = (lo + hi) / 2;
4. If A_m  <T, set L to m+1 or if A_m  >T, set R to m-1 and go to step 2 
> ``` Java
> if (key < array[mid]) {
>   // hi = mid -1;
>   return BinarySearch(array, lo, mid - 1, key);
> } else if (key > array[mid]) {
>   // lo = mid + 1;
>   return BinarySearch(array, mid + 1, hi, key);
> }
5. Now, f A_m=T, the search is completed and returns m.
> ``` Java
> if (key == array[mid]) {
>   // Return index value.
>   return mid;
> }
  
