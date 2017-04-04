import java.util.Arrays;

/**
 * Created by wcc on 17-4-3.
 */
public class TestSort {
    public static void main(String args[]){
        int[] test1={2,5,9,56,-1,5,4,99,105,-89,48,1000,6,68,2017,56,-1000,568,1,0,7,117,4,99,105,-89,48,1000,6,68,2017,56,-10};
        int[] test2={2,5,9};
        System.out.println(Arrays.toString(insert_sort(test1)));
        System.out.println(Arrays.toString(select_sort(test1)));
        System.out.println(Arrays.toString(merge_sort(test1)));
        System.out.println(Arrays.toString(heap_sort1(test1)));
        System.out.println(Arrays.toString(heap_sort2(test1)));
    }

    //insert_sort 原始的插入排序，是不用linkedlist的
    public static int[] insert_sort(int[] arr){
        int[] result=Arrays.copyOf(arr,arr.length);
        for(int i=1;i<result.length;i++){
            int temp=result[i];
            int j=i-1;
            for(;(j>=0)&&(result[j]>temp);j--){
                result[j+1]=result[j];
            }
            result[j+1]=temp;
        }
        return result;
    }

    //select_sort
    public static int[] select_sort(int[] arr){
        int[] result=Arrays.copyOf(arr,arr.length);
        for(int i=0;i<arr.length;i++){
            int min=result[i];
            int index=i;
            for(int j=i+1;j<arr.length;j++){
                if(result[j]<min){
                    min=result[j];
                    index=j;
                }
            }
            result[index]=result[i];
            result[i]=min;
        }
        return result;
    }

    //merge_sort
    public static int[] merge_sort(int[] arr){
        if(arr.length<=1){
            return arr;
        }
        else{
            int[] left=merge_sort(Arrays.copyOfRange(arr,0,arr.length/2));
            int[] right=merge_sort(Arrays.copyOfRange(arr,arr.length/2,arr.length));
            int[] result=new int[arr.length];
            int i=0;
            int j=0;
            for(int k=0;k<arr.length;k++){
                if((i==left.length)||(j==right.length)){
                    if(i==left.length){
                        result[k]=right[j++];
                    }
                    else{
                        result[k] =left[i++];
                    }
                }
                else{
                    if(left[i]>right[j]){
                        result[k]=right[j++];
                    }
                    else{
                        result[k]=left[i++];
                    }
                }
            }
            return result;
        }
    }

    //heap_sort1 in-place堆排序，即直接将堆反转成已排序列，空复O(1).
    //因此，要构建一个大根堆，堆顶元素剔除后，要放到堆末，DeleteMax操作时的空穴由左右儿子和原来的堆末元素，三者比较的最大者填充。
    //由于又要判断左右儿子是否超出了堆的范围，切实三者比较大小，所以造成了代码的复杂
    //如果不追求in-place堆排序，逻辑就简介很多了，见heap_sort2
    public static int[] heap_sort1(int[] arr){
        //int[] result=new int[arr.length];
        int[] heap=new int[arr.length+1];
        for(int i=0;i<arr.length;i++){
            int j=i+1;
            heap[j]=arr[i];
            while(j!=1){
                if(heap[j]>heap[j/2]){//为了实现in-place降序排序，构建的要是一个大根堆“对顶元素为最大值”
                    int temp=heap[j];
                    heap[j]=heap[j/2];
                    heap[j/2]=temp;
                    j=j/2;
                }
                else{
                    break;
                }
            }
        }
        for(int i=0;i<arr.length;i++){
            int max=heap[1];
            int empty_index=1;
            int heapLast=heap.length-i-1;
            while(true){
                if(empty_index*2>heapLast){//左右儿子均超出堆范围的情况
                    heap[empty_index]=heap[heapLast];
                    heap[heapLast]=max;
                    break;
                }
                if(empty_index*2+1>heapLast){//仅右儿子超出堆范围的情况
                    if(heap[empty_index*2]>heap[heapLast]){
                        heap[empty_index]=heap[heapLast];
                        heap[heapLast]=max;
                        break;
                    }
                    else{
                        heap[empty_index]=heap[empty_index*2];
                        empty_index=empty_index*2;
                        continue;
                    }
                }
                else{//左右儿子均没有超出堆范围的情况
                    if((heap[empty_index*2]>=heap[heapLast])&&(heap[empty_index*2]>=heap[empty_index*2+1])){
                        //左儿子最大
                        heap[empty_index]=heap[empty_index*2];
                        empty_index=empty_index*2;
                        continue;
                    }
                    if((heap[empty_index*2+1]>=heap[heapLast])&&(heap[empty_index*2+1]>=heap[empty_index*2])){
                        //右儿子最大
                        heap[empty_index]=heap[empty_index*2+1];
                        empty_index=empty_index*2+1;
                        continue;
                    }
                    if((heap[heapLast]>=heap[empty_index*2+1])&&(heap[heapLast]>=heap[empty_index*2])){
                        //堆末元素最大
                        heap[empty_index]=heap[heapLast];
                        heap[heapLast]=max;
                        break;
                    }
                }
            }
        }
        return Arrays.copyOfRange(heap,1,heap.length);
    }

    //heap_sort2 DeleteMin操作将最小元素弹到新的数组里，因而不再需要移动堆末元素，故由谁填空穴只需要比较空穴的左右儿子，
    //DeleteMin操作在堆底产生的空穴，填充进Integer.MAX_VALUE，相当于是标记了这个位置为空
    public static int[] heap_sort2(int[] arr){
        int[] result=new int[arr.length];
        int[] heap=new int[arr.length+1];
        for(int i=0;i<arr.length;i++){
            int j=i+1;
            heap[j]=arr[i];
            while(j!=1){
                if(heap[j]<heap[j/2]){
                    int temp=heap[j];
                    heap[j]=heap[j/2];
                    heap[j/2]=temp;
                    j=j/2;
                }
                else{
                    break;
                }
            }
        }
        for(int i=0;i<arr.length;i++){
            result[i]=heap[1];
            int empty_index=1;
            int heapLast=heap.length-1;
            while(true){
                if(empty_index*2>heapLast){//左右儿子均超出堆范围的情况
                    heap[empty_index]=Integer.MAX_VALUE;
                    break;
                }
                if(empty_index*2+1>heapLast){//仅右儿子超出堆范围的情况
                        heap[empty_index]=heap[empty_index*2];
                        break;
                    }
                else{//左右儿子均没有超出堆范围的情况
                    if(heap[empty_index*2]<heap[empty_index*2+1]){
                        heap[empty_index]=heap[empty_index*2];
                        empty_index=empty_index*2;
                    }
                    else{
                        heap[empty_index]=heap[empty_index*2+1];
                        empty_index=empty_index*2+1;
                    }
                }
            }
        }
        return result;
    }

    public static void quick_sort(int[] arr){

    }
}


