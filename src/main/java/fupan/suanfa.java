package fupan;

import java.util.*;

public class suanfa {
}

//快排
class QuickSort {
    public QuickSort(int[] nums){
        int n = nums.length;
        sort(nums, 0, n-1);
    }
    public void sort(int[] nums, int l, int r){
        if(l >= r) return;
        int pivot = nums[l];
        int i = l, j = r;
        while(i <= j) {
            while (i<r && nums[i] <= pivot) i++;
            while (j>l && nums[r] > pivot) j--;
            if (i >= j) break;
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        int temp = nums[l];
        nums[l] = nums[j];
        nums[j] = temp;
        sort(nums, l, j-1);
        sort(nums,j+1, r);
    }
}

class nSum{
    List<List<Integer>> ans = new LinkedList<>();
    LinkedList<Integer> path = new LinkedList<>();

    //三数之和
    public List<List<Integer>> threeSum(int[] nums){
        //a b c不同，且和为0
        //重点是去重
        Arrays.sort(nums);
        int n = nums.length;
        for(int i=0; i<n; i++){
            if(i>0 && nums[i]==nums[i-1]) continue;
            int l = i + 1, r = n - 1;
            while(l < r){
                int sum = nums[i] + nums[l] + nums[r];
                if(sum == 0){
                    path.add(nums[i]);
                    path.add(nums[l]);
                    path.add(nums[r]);
                    ans.add(new LinkedList<>(path));
                    path.clear();
                    l++;
                    r--;
                    //去重
                    while(l<r && nums[l]==nums[l-1]) l++;
                    while(r>l && nums[r]==nums[r+1]) r--;
                } else if(sum < 0) l++;
                else if(sum > 0) r--;
            }
        }
        return ans;
    }
    public void backtracking(int[] nums, int u, int sum){
        int n = nums.length;
        if(path.size() > 3 || u >= n) return;
        if(path.size() == 3){
            if(sum == 0){
                ans.add(new LinkedList<>(path));
                return;
            } else{
                return;
            }
        }
        for(int i=u; i<n; i++){
            while(i>u && i<n && nums[i]==nums[i-1]) i++;
            if(i == n) break;
            path.add(nums[i]);
            backtracking(nums, i+1, sum+nums[i]);
            path.removeLast();
        }
    }
        //有效的符号  //栈的应用
        public boolean isValid(String s) {
            Stack<Character> stk = new Stack<>();
            char[] chars = s.toCharArray();
            int n = s.length();
            if(n == 0) return true;
            int i = 0;
            while(i<n) {
                if(stk.isEmpty()) stk.push(chars[i]);
                else {
                    char c = stk.peek();
                    if((int)c+1 == chars[i] || (int)c+2 == chars[i]) stk.pop();
                    else stk.push(chars[i]);
                }
                i++;
            }
            if(stk.isEmpty()) return true;
            return false;
        }

        //买卖股票
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int[][] dp = new int[n][2];
            dp[0][0] = -prices[0];
            for(int i=1; i<n; i++) {
                dp[i][0] = Math.max(dp[i-1][0], -prices[i]);
                dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]+prices[i]);
            }
            return dp[n-1][1];
        }

        //买两次
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[] dp = new int[5];
        dp[1] = -prices[0];
        for(int i=1; i<n; i++) {
            int d1 = dp[1], d2 = dp[2], d3 = dp[3];
            dp[1] = Math.max(dp[0], -prices[i]);
            dp[2] = Math.max(dp[2], d1+prices[i]);
            dp[3] = Math.max(dp[3], d2-prices[i]);
            dp[4] = Math.max(dp[4], d3+prices[i]);
        }
        return dp[4];
    }

    //买无数次
    public int maxProfit3(int[] prices){
        int n = prices.length;
        int[] dp = new int[2];
        dp[0] = -prices[0];
        int ans = Integer.MIN_VALUE;
        for(int i=1; i<n; i++) {
            int d0 = dp[0];
            dp[0] = Math.max(dp[0], dp[1]-prices[i]);
            dp[1] = Math.max(dp[1], d0+prices[i]);
            ans = dp[1] > ans ? dp[1] : ans;
        }
        return dp[1];
    }

    //交易四次
    public int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        int[] dp = new int[k*2+1];
        for(int i=1; i<k*2+1; i++) {
            if(i % 2 == 1) dp[i] = -prices[0];
        }

        for(int i=1; i<n; i++){
            for(int j=1; j<k*2+1; j++){
                if(j%2 == 0) dp[j] = Math.max(dp[j], dp[j-1]+prices[i]);
                else dp[j] = Math.max(dp[j], dp[j-1]-prices[i]);
            }
        }
        return dp[k*2];
    }

    //含有冷冻期
    public int maxProfix5(int[] prices){
        int n = prices.length;
        int[] dp = new int[5];
        dp[1] = -prices[0];
        for(int i=0; i<n; i++){
            int d1 = dp[1], d2 = dp[2], d3 = dp[3];
            dp[1] = Math.max(dp[1], Math.max(dp[3]-prices[i], dp[4]-prices[i]));
            dp[2] = Math.max(dp[2], d1+prices[i]);
            dp[3] = Math.max(dp[3], d2);
            dp[4] = Math.max(dp[4], d3);
        }
        return Math.max(dp[2], Math.max(dp[3], dp[4]));
    }

    public int maxProfit6(int[] prices, int fee){
        int n = prices.length;
        int[] dp = new int[2];
        dp[0] = -prices[0];
        for(int i=1; i<n; i++){
            int d0 = dp[0];
            dp[0] = Math.max(dp[0], dp[1]-prices[i]);
            dp[1] = Math.max(dp[1], d0+prices[i]-fee);
        }
        return dp[1];
    }

    int a = -1, b = -1, len = 0;
    public void extend(String s, int l, int r) {
        int n = s.length();
        while(l>=0 && r<n && s.charAt(l)==s.charAt(r)) {
            int t = r - l + 1;
            if(t > len) {
                len = t;
                a = l;
                b = r;
            }
            l--;
            r++; //扩散
        }
    }

    //岛屿数量
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j] == '1'){
                    dfs(grid, i, j);
                    ans++;
                }
            }
        }
        return ans;
    }

    public void dfs(char[][] grid, int i, int j){
        int m = grid.length, n = grid[0].length;
        if(i>=0 && i<m && j>=0 && j<n && grid[i][j]=='1'){
            grid[i][j] = '0';
            dfs(grid, i-1, j);
            dfs(grid, i+1, j);
            dfs(grid, i, j-1);
            dfs(grid, i, j+1);
        }
    }

    public void bfs(char[][] grid, int i, int j){
        int m = grid.length, n = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        while(!q.isEmpty()){
            int[] t = q.poll();
            if(t[0]>=0 && t[0]<m && t[1]>=0 && t[1]<n && grid[t[0]][t[1]]=='1'){
                grid[t[0]][t[1]] = '0';
                q.add(new int[]{i-1, j});
                q.add(new int[]{i+1, j});
                q.add(new int[]{i, j-1});
                q.add(new int[]{i, j+1});
            }
        }
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
    }

    //层序遍历 S形
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<>();
        Deque<Integer> path = new LinkedList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if(root == null) return ans;
        q.add(root);
        int level = 1;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0; i<size; i++) {
                TreeNode p = q.poll();
                if (level % 2 == 1) path.offerLast(p.val);
                else path.addFirst(p.val);
                if (p.left != null) q.add(p.left);
                if (p.right != null) q.add(p.right);
            }
            level++;
            ans.add(new LinkedList<>(path));
            path.clear();
        }
        return ans;
    }

    //最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p, q);
        if(l != null && r != null) return root;
        if(l != null && r == null) return l;
        if(l == null && r != null) return r;
        return null;
    }
    class Backtracking{
        List<List<Integer>> ans = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();
        boolean[] used;
        //全排列 //不含重复元素
        public List<List<Integer>> permute(int[] nums) {
            used = new boolean[nums.length];
            backtracking(nums);
            return ans;
        }
        public void backtracking(int[] nums){
            int n = nums.length;
            if(path.size() > n) return;
            if(path.size() == n){
                ans.add(new LinkedList<>(path));
                return;
            }
            for(int i=0; i<n; i++){
                if(used[i]) continue;
                used[i] = true;
                path.add(nums[i]);
                backtracking(nums);
                path.removeLast();
                used[i] = false;
            }
        }
        //含有重复元素
        public List<List<Integer>> pailie(int[] nums){
            Arrays.sort(nums);
            used = new boolean[nums.length];
            backtracking2(nums);
            return ans;
        }
        public void backtracking2(int[] nums){
            int n = nums.length;
            if(path.size() > n) return;
            if(path.size() == n){
                ans.add(new LinkedList<>(path));
                return;
            }
            for(int i=0; i<n; i++){
                if(used[i]) continue;
                if(i>0 && nums[i]==nums[i-1]) continue;
                path.add(nums[i]);
                used[i] = true;
                backtracking2(nums);
                used[i] = false;
                path.removeLast();
            }
        }

        //划分子集 无重复元素
        public List<List<Integer>> subsets(int[] nums) {
            backtracking3(nums, 0);
            return ans;
        }
        public void backtracking3(int[] nums, int u){
            int n = nums.length;
            if(path.size() > n) return;
            ans.add(new LinkedList<>(path));
            for(int i=u; i<n; i++){
                path.add(nums[i]);
                backtracking3(nums, i+1);
                path.removeLast();
            }
        }
    }


}
