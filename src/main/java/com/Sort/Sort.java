package com.Sort;


import java.util.Scanner;


public class Sort {
    public static void main(String[] args) {
        //处理输入
        //先输入一个整数n，表示接下来要输入n个整数
        //接下来再输入n个整数
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for(int i=0; i<n; i++) {
            nums[i] = sc.nextInt();
        }

        //QuickSort sort = new QuickSort(n, nums);
        //sort.sorted(nums, 0, n-1);

        //测试归并排序
        MergeSort sort = new MergeSort(n, nums);
        sort.sorted(nums, 0, n-1);

        for(int i=0; i<n; i++)
        System.out.println(nums[i]);
    }
}
//手写快排
/*
class QuickSort{
    int[] nums;

    public QuickSort(int n, int[] nums){
        this.nums = new int[n];
        for(int i=0; i<n; i++)
            this.nums[i] = nums[i];
    }

    public void sorted(int[] nums, int left, int right){
        if(left >= right) return;

        int pivot = nums[left];

        int i = left + 1, j = right;
        while(i <= j){
            while(i<right && nums[i] < pivot) i++;
            while(j>left && nums[j] > pivot) j--;
            if(i >= j) break;
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        int t = nums[j];
        nums[j] = nums[left];
        nums[left] = t;

        sorted(nums, left, j-1);
        sorted(nums, j+1, right);
    }
} */

//在写一次快排
class QuickSort{
    public QuickSort(){
    }

    public void sorted(int[] nums, int l, int r){
        if(l >= r) return;
        int pivot = nums[l];
        int i = l + 1, j = r;
        while(i <= j){
            while(i<r && nums[i]<pivot) i++;
            while(j>l && nums[j]>pivot) j--;
            if(i >= j) break;
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

        int temp = nums[l];
        nums[l] = nums[j];
        nums[j] = temp;

        sorted(nums, l, j-1);
        sorted(nums, j+1, r);
    }
}

//归并
class MergeSort{
    int[] nums;
    public MergeSort(int n, int[] nums){
        this.nums = new int[n];
        for(int i=0; i<n; i++)
            this.nums[i] = nums[i];
    }

    public void sorted(int[] nums, int l, int r){
        if(l >= r) return;

        int mid = l + (r - l) / 2;
        int[] temp = new int[nums.length];
        for(int i=0; i<nums.length; i++) temp[i] = nums[i];
        sorted(temp, l, mid);
        sorted(temp, mid+1, r);


        //合并，二叉树后序遍历
        for(int i=l,j=mid+1,p=l; p<=r; p++){
            if(i > mid) nums[p] = temp[j++];
            else if(j > r) nums[p] = temp[i++];
            else if(temp[i] < temp[j]) nums[p] = temp[i++];
            else if(temp[i] >= temp[j]) nums[p] = temp[j++];
        }
    }
}
