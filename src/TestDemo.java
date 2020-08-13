
/*
*
*                 22号的demo  3 4  5
* */

public class TestDemo{


    static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }


    private TreeNode lca = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null){
            return  null;
        }
        findNode(root,p,q);
        return lca;
    }

    //如果在root 中找到p或者q，返回true 否则返回false
    private boolean findNode(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null){
            return false;
        }
        //采用后序遍历（不管哪种遍历都行）进行查找
        int left = findNode(root.left,p,q) ? 1 : 0;
        int right = findNode(root.right,p,q) ? 1 : 0;
        int mid = (root == p || root == q) ? 1 : 0;
        //只要在left ，right 或者根节点中出现两个 那么就是最近公共祖先
        if (left + right + mid == 2 ){
            lca = root;
        }
        return (left + right + mid) > 0 ;
    }








    //返回值的含义表示链表的头结点
    public TreeNode Convert(TreeNode root) {
        if (root == null){
            return null;
        }
        if (root.left == null && root.right == null){
            //只有一个根节点 得到的链表也就只有一个根节点
            return root;
        }

        //这里递归要用中序遍历


        //这里面left相当于链表中的prve，riht相当于链表中的next

        //先递归左子树
        //这个方法一执行就把这棵树的左子树完整转换为双向链表
        //left表示链表的头结点
        TreeNode left = Convert(root.left);



        //再处理根节点
        //此时需要用尾插 定义一个leftTail等于链表头部left然后循环 将其置为链表尾部为后面连接根节点准备
        TreeNode leftTail = left;
        while (leftTail != null && leftTail.right != null){
            leftTail = leftTail.right;
        }
        //上面循环结束 就说明leftTail就是left链表的最后一个节点
        //这里if的判断语句是防止left为空 然后leftTail也是null
        if (leftTail != null){
            //这里是连接左子树链表和 根节点链表的头部
            leftTail.right = root;
            root.left = leftTail;
        }



        //最后处理右子树,先定义一个right表示右子树链表的头部
        TreeNode right = Convert(root.right);
        //判断一下右子树是否为空，不为空则 链接头结点的链表和右子树的链表
        if (right != null){
            right.left = root;
            root.right = right;
        }


        //这个方法返回的是链表的头节点 这里判断left为空不，为空就返回root
        return left != null ? left : root;
    }








    //preorder和inorder这两个数组的长度肯定相同
    //为了辅助遍历定义一个index搞清楚preorder访问到哪个元素
    private int index = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        index = 0;
        return buildTreeHelper(preorder,inorder,0,inorder.length);
    }

    //[inorderLeft,inorderRight)表示当前子树中序遍历的区间
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int inorderLeft, int inorderRight) {
        if (inorderLeft >= inorderRight){
            return  null;
        }
        if (index >= preorder.length){
            return null;
        }
        //根据index取出当前树的根节点值，并构造根节点
        TreeNode newNode = new TreeNode(preorder[index]);
        //知道根节点之后，还需要根据根节点在中序遍历中确定左右子树范围

        int pos = find(inorder,inorderLeft,inorderRight,newNode.val);
        index++;
        //用find找到pos位置之后就可以确定左右子树的范围
        //下面例子中3是根节点 对应的pos=1， 3左边对应下标[0,1)这是左子树在中序遍历的区间
        //3右边对应下标[2,5)这是右子树在中序遍历的区间
        //例：前序遍历 preorder = [3,9,20,15,7]  中序遍历 inorder = [9,3,15,20,7]
        //左子树对应中序遍历的下标区间：[inorderleft，pos）
        //右子树对应中序遍历的下标区间：[pos+1,inorderright)
        newNode.left = buildTreeHelper(preorder,inorder,inorderLeft,pos);
        newNode.right = buildTreeHelper(preorder,inorder,pos+1,inorderRight);
        return newNode;
    }

    //用find这个方法当前节点在中序遍历中的位置
    private int find(int[] inorder, int inorderLeft, int inorderRight, int val) {
        for (int i = inorderLeft; i <inorderRight ; i++) {
            if (inorder[i] == val){
                return i;
            }
        }
        return -1;
    }
}