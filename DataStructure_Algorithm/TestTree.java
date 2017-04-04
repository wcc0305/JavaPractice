import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import static java.lang.Integer.max;
import static java.lang.Math.pow;

public class TestTree {
    public static void main(String args[]){
        //String str="1,2,3,4,5,#,6,7,8,#,#,#,#,9,#";
        String str1="1,2,3,4,5,6,#,7,#,8,9,10,#,#,#,11,#,#,#,12,13,14,#,15,#,#,#,#,#,#,#";
        String str2="(1,2);(#);(5,6);(7)";
        String str3="(1,2);(3,4);(5,6);(7,8);(#);(9,10);(#);(11,12);(13,14);(15,16);(17,18);(#);(#);" +
                "(#);(#);(#);(19,20);(#);(#);(#);(#);(#);(#);(#);(#);(21,22);(#);(23,24);(25,26);(#);(#);"+
                "(#);(#);(#);(#);(#);(#);(#);(#);(#)";
        Tree_23 tree_23=new Tree_23(str3);
        tree_23.printTree();
        System.out.println();
        BinaryTree tree=new BinaryTree(str1);
        tree.printTree();
    }
}
class BinaryTree{
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val){
            this.val=val;
        }
    }
    private TreeNode root;
    private final int maxHeight=6;//还没处理
    private final int eleLength=4;
    public BinaryTree(TreeNode root){
        this.root=root;
    }
    public BinaryTree(String str){
        String[] treeStr=str.split(",");
        TreeNode root=new TreeNode(Integer.parseInt(treeStr[0]));
        LinkedList<TreeNode> list=new LinkedList<>();
        list.add(root);
        int i=0;
        while(++i<treeStr.length){
            if(!treeStr[i].equals("#")){
                list.peek().left=new TreeNode(Integer.parseInt(treeStr[i]));
                list.add(list.peek().left);
            }
            else{
                list.add(new TreeNode(0));
            }
            if(!treeStr[++i].equals("#")){
                list.peek().right=new TreeNode(Integer.parseInt(treeStr[i]));
                list.add(list.peek().right);
            }else{
                list.add(new TreeNode(0));
            }
            list.pop();
        }
        this.root=root;
    }
    public void printTree(){
        int height=getHeight(this.root);
        int totalLength=(int)pow(2,height-1)*eleLength;//(int)pow(2,height+1);
        LinkedList<TreeNode> list=new LinkedList<>();
        list.add(this.root);
        HashSet<Integer> set=new HashSet<>();
        set.add(((int)pow(2,height-1)-1)*eleLength/2);
        for(int i=0;i<height;i++){
            boolean flag=true;
            StringBuilder str1=new StringBuilder();
            StringBuilder str2=new StringBuilder();
            for(int j=0;j<totalLength;j++){
                if(set.contains(j)){
                    if(list.peek().val!=Integer.MIN_VALUE){
                        str1.append(Integer.parseInt(String.valueOf(list.peek().val)));
                        str1.append(nSpaces(eleLength-String.valueOf(list.peek().val).length()));
                        str2.append(flag ? "/" : "\\").append(nSpaces(eleLength-1));
                        flag=!flag;
                        if(list.peek().left!=null){
                            list.add(list.peek().left);
                        }else{
                            list.add(new TreeNode(Integer.MIN_VALUE));
                        }
                        if(list.peek().right!=null){
                            list.add(list.peek().right);
                        }else{
                            list.add(new TreeNode(Integer.MIN_VALUE));
                        }
                        list.pop();
                    }
                    else{
                        //str1.append(nSpaces(eleLength));
                        //str2.append(nSpaces(eleLength));
                        str1.append("#"+nSpaces(eleLength-1));//还是把空节点'#'打印出来比较清晰
                        str2.append(flag ? "/" : "\\").append(nSpaces(eleLength-1));
                        flag=!flag;
                        list.add(new TreeNode(Integer.MIN_VALUE));
                        list.add(new TreeNode(Integer.MIN_VALUE));
                        list.pop();
                    }
                    j=j+eleLength-1;
                }
                else{
                    str1.append(" ");
                    str2.append(" ");
                }
            }
            ArrayList<Integer> arr=new ArrayList<>();
            for(Integer a:set) {
                arr.add(a);
            }
            set.clear();
            for(Integer a:arr){
                set.add(a+(int)(pow(2,height-i-3)*eleLength));
                set.add(a-(int)(pow(2,height-i-3)*eleLength));
            }
            if(i!=0){
                System.out.println(str2.toString());
            }
            System.out.println(str1.toString());
        }
    }
    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return 1+max(getHeight(root.left), getHeight(root.right));
        }
    }
    public String nSpaces(int n){
        StringBuilder strb=new StringBuilder();
        for(int i=0;i<n;i++){
            strb.append(" ");
        }
        return strb.toString();
    }
}

class Tree_23{
    class TreeNode{
        int leftValue;
        int rightValue;
        TreeNode leftNode;
        TreeNode cenNode;
        TreeNode rightNode;
        public TreeNode(String str){
            str=str.substring(1,str.length()-1);//脱去括号
            if(str.equals("#")){
                this.leftValue=Integer.MIN_VALUE;
                this.rightValue=Integer.MIN_VALUE;
            }
            else{
                String[] treeNode=str.split(",");
                this.leftValue=Integer.parseInt(treeNode[0]);
                if(treeNode.length==2){
                    this.rightValue=Integer.parseInt(treeNode[1]);
                }else{
                    this.rightValue=Integer.MIN_VALUE;
                }
            }
        }
        public boolean isEmpty(){
            return (this.leftValue==Integer.MIN_VALUE)&&(this.rightValue==Integer.MIN_VALUE);
        }
        public String toString(){
            StringBuilder strb=new StringBuilder("(");
            if(this.isEmpty()) {
                strb.append("#");
            }
            else{
                strb.append(String.valueOf(this.leftValue));
                if(this.rightValue!=Integer.MIN_VALUE){
                    strb.append(","+String.valueOf(this.rightValue));
                }
            }
            strb.append(")");
            return strb.toString();
        }
    }
    private TreeNode root;
    private final int maxHeight=4;//还没处理
    private final int eleLength=8;
    public Tree_23(TreeNode root){
        this.root=root;
    }
    public Tree_23(String str){
        String[] treeStr=str.split(";");
        TreeNode root=new TreeNode(treeStr[0]);
        LinkedList<TreeNode> list=new LinkedList<>();
        list.add(root);
        int i=0;
        while(i<(treeStr.length-1)/3){
            if(!list.peek().isEmpty()){
                list.peek().leftNode=new TreeNode(treeStr[3*i+1]);
                list.add(list.peek().leftNode);
                list.peek().cenNode=new TreeNode(treeStr[3*i+2]);
                list.add(list.peek().cenNode);
                list.peek().rightNode=new TreeNode(treeStr[3*i+3]);
                list.add(list.peek().rightNode);
            }
            else{
                list.add(new TreeNode("(#)"));
                list.add(new TreeNode("(#)"));
                list.add(new TreeNode("(#)"));
            }
            list.pop();
            i++;
        }
        this.root=root;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return 1+max(max(getHeight(root.leftNode), getHeight(root.rightNode)),max(getHeight(root.rightNode),getHeight(root.cenNode)));
        }
    }
    public String nSpaces(int n){
        StringBuilder strb=new StringBuilder();
        for(int i=0;i<n;i++){
            strb.append(" ");
        }
        return strb.toString();
    }
    public void printTree() {
        int height = getHeight(this.root);
        int totalLength = (int) pow(3, height - 1) * eleLength;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(this.root);
        HashSet<Integer> set = new HashSet<>();
        set.add((((int) pow(3, height - 1)) - 1) / 2 * eleLength);
        for (int i = 0; i < height; i++) {
            int flag = 0;
            StringBuilder str1 = new StringBuilder();
            StringBuilder str2 = new StringBuilder();
            for (int j = 0; j < totalLength; j++) {
                if (set.contains(j)) {
                    str1.append(list.peek().toString());
                    str1.append(nSpaces(eleLength - list.peek().toString().length()));
                    switch (flag) {
                        case 0:
                            str2.append("/");
                            break;
                        case 1:
                            str2.append("|");
                            break;
                        case 2:
                            str2.append("\\");
                            break;
                    }
                    str2.append(nSpaces(eleLength - 1));
                    flag = (flag + 1) % 3;
                    if (list.peek().isEmpty()) {
                        list.add(new TreeNode("(#)"));
                        list.add(new TreeNode("(#)"));
                        list.add(new TreeNode("(#)"));
                    } else {
                        if (list.peek().leftNode != null) {
                            list.add(list.peek().leftNode);
                        } else {
                            list.add(new TreeNode("(#)"));
                        }
                        if (list.peek().cenNode != null) {
                            list.add(list.peek().cenNode);
                        } else {
                            list.add(new TreeNode("(#)"));
                        }
                        if (list.peek().rightNode != null) {
                            list.add(list.peek().rightNode);
                        } else {
                            list.add(new TreeNode("(#)"));
                        }
                    }
                    list.pop();
                    j = j + eleLength - 1;
                } else {
                    str1.append(nSpaces(1));
                    str2.append(nSpaces(1));
                }
            }
            ArrayList<Integer> arr=new ArrayList<>();
            for(Integer a:set) {
                arr.add(a);
            }
            for(Integer a:arr){
                set.add(a+(int)pow(3,height-i-2)*eleLength);
                set.add(a-(int)pow(3,height-i-2)*eleLength);
            }
            if (i != 0) {
                System.out.println(str2.toString());
            }
            System.out.println(str1.toString());
        }
    }
}


