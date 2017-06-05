public class Tester {

    private static int failedTests=0;
    private static int passedTests=0;
    final private static int baseElement=1000;
    final private static int secondBaseElement=100;

    public static void main(String[] args) {

        /* Base tests */
        DHeap Heap =new DHeap(4,1000);
        for(int i=1;i<=20;i++)
        {
            DHeap_Item temp1=new DHeap_Item("scenario0",i);
            Heap.Insert(temp1);
        }

        Heap.Delete(Heap.Get_Min());
        Heap.printHeap();
        Heap.Delete(Heap.Get_Min());
        Heap.printHeap();


		/* 10 main scenarios */

		/* First test- Sanity check */
        DHeap Heap4=new DHeap(4,1000);
        DHeap Heap7=new DHeap(7,1000);
        DHeap_Item heap1Items[]=new DHeap_Item[100];
        DHeap_Item heap2Items[]=new DHeap_Item[100];

        performSanityTest(Heap4,Heap7);

        System.out.println("First Test: " + passedTests);

		/* Second test - insert 1,000 elements to each heap in a scrambled order */
		/* elements: 0 , 2, 4 , .... , 998, 1002, ... 2000*/

		/* make sure that min is correct element and heap size is correct */
        for(int i=1;i<=500;i++)
        {
            DHeap_Item temp1=new DHeap_Item("scenario2",baseElement-2*i);
            DHeap_Item temp2=new DHeap_Item("scenario2",baseElement+2*i);
            DHeap_Item temp3=new DHeap_Item("scenario2",baseElement-2*i);
            DHeap_Item temp4=new DHeap_Item("scenario2",baseElement+2*i);
            Heap4.Insert(temp1);
            Heap4.Insert(temp2);
            Heap7.Insert(temp3);
            Heap7.Insert(temp4);
        }

        performMinAndSizeTest(Heap4,Heap7,0,1000);

        System.out.println("Second Test: " + passedTests);


		/* Third test - 500 times delete min */
		/* remaining elements should be  : 1002, 1004 , .... 2000 */

        for(int i=1;i<=500;i++)
        {
            Heap4.Delete_Min();
            Heap7.Delete_Min();
        }
        performMinAndSizeTest(Heap4,Heap7,1002,500);

        System.out.println("Third Test: " + passedTests);

		/* Fourth test - insert additional 100 elements */
		/* 149, 151, 148, 152 ... 50, 150 */
        for(int i=1;i<=50;i++)
        {
            DHeap_Item temp1=new DHeap_Item("scenario4",secondBaseElement-i);
            DHeap_Item temp2=new DHeap_Item("scenario4",secondBaseElement+i);
            DHeap_Item temp3=new DHeap_Item("scenario4",secondBaseElement-i);
            DHeap_Item temp4=new DHeap_Item("scenario4",secondBaseElement+i);
            Heap4.Insert(temp1);
            Heap4.Insert(temp2);
            Heap7.Insert(temp3);
            Heap7.Insert(temp4);
        }

        performMinAndSizeTest(Heap4,Heap7,50,600);

        System.out.println("Forth Test: " + passedTests);

		/* Fifth test - delete minimum, get min and decrease key*/
        Heap4.Delete_Min();
        Heap7.Delete_Min();
		/* now minimum should be == 51 */
        try{
            DHeap_Item firstHeapMin=Heap4.Get_Min();
            Heap4.Decrease_Key(firstHeapMin,26);
        } catch (Exception e) {
            System.out.println("exception");
        } try {
            DHeap_Item secondHeapMin=Heap7.Get_Min();
            Heap7.Decrease_Key(secondHeapMin,26);
        } catch (Exception e) {
            System.out.println("exception");
        }


        performMinAndSizeTest(Heap4,Heap7,25,599);

        System.out.println("Fifth Test: " + passedTests);

		/* sixth test - deleting all elements but one */

        for(int i=1; i<=598 ; i++)
        {
            Heap4.Delete_Min();
            Heap7.Delete_Min();
        }

        performMinAndSizeTest(Heap4,Heap7,2000,1);

        System.out.println("Sixth Test: " + passedTests);

		/* seventh test - using array to heap*/
		/* building an array : 1, 100, 2, 99, 3... 50 , 51 */
        for(int i=1;i<=100;i++)
        {
            int j=0;
            if(i%2 == 1 )
            {
                j=i/2+1;
            }
            else
            {
                j= 100-(i/2-1);
            }
            DHeap_Item temp1=new DHeap_Item("scenario7", j);
            DHeap_Item temp2=new DHeap_Item("scenario7", j);
            heap1Items[i-1]=temp1;
            heap2Items[i-1]=temp2;

        }
        try{
            Heap4.arrayToHeap(heap1Items);
        } catch (Exception e) {
            System.out.println("Exception in 7th test");
            e.printStackTrace();
        }
        try{
            Heap7.arrayToHeap(heap2Items);
        }
        catch (Exception e){
            System.out.println("Exception in 7th test");
            e.printStackTrace();

        }

        performMinAndSizeTest(Heap4,Heap7,1,100);

        System.out.println("Seventh Test: " + passedTests);

		/* eighth test  - create an additional heap with d = 13, max elements = 2,500 */
		/* insert each elements twice, meaning inserting: 1250, 1250, 1249,1249 ... 1, 1 */
        DHeap Heap13= new DHeap(13, 2500);
        for(int i=1250; i>=1 ; i--)
        {
            DHeap_Item temp1=new DHeap_Item("scenario8", i);
            DHeap_Item temp2=new DHeap_Item("scenario8", i);
            Heap13.Insert(temp1);
            Heap13.Insert(temp2);
        }
        performMinSizeAndIsHeapTest(Heap13,2500,1,true);
        DHeap_Item minItem=Heap13.Get_Min();
        Heap13.Decrease_Key(minItem,4);
        performMinSizeAndIsHeapTest(Heap13,2500,-3,true);

        System.out.println("Eighth Test: " + passedTests);
		/* ninth test - create an additional heap, and compare elements with heap 13*/

        DHeap Heap3=new DHeap(3,2500);
        for(int i=1;i<=1250;i++)
        {
            DHeap_Item temp1;
            DHeap_Item temp2;
            if(i==1)
            {
                temp1=new DHeap_Item("scenario9",-3);
                temp2=new DHeap_Item("scenario9", 1);
            }
            else
            {
                temp1=new DHeap_Item("scenario9", i);
                temp2=new DHeap_Item("scenario9", i);
            }
            Heap3.Insert(temp1);
            Heap3.Insert(temp2);
        }

        boolean compareHeapsMin=true;
        boolean compareHeapsSize=true;
        for(int i=1; i<=1250 ; i++)
        {
            int firstMin=Heap13.Get_Min().getKey();
            int secondMin=Heap3.Get_Min().getKey();
            if(firstMin != secondMin)
            {
                compareHeapsMin=false;
            }
            if(Heap13.getSize() != Heap3.getSize())
            {
                compareHeapsSize=false;
            }
        }

        count2BooleansAsTest(compareHeapsMin,compareHeapsSize);

        System.out.println("Ninth Test: " + passedTests);

		/* final test- checking DHeapSort on 2 arrays */
		/* build 2 scrambled arrays with "random" (yet, deterministic) values */

        int firstScrambledArray[]= new int[250];
        int secondScrambledArray[]= new int[250];
        for(int i=1; i<= 250; i++)
        {
            int j=0;
            switch (i%4)
            {
                case 0:
                    j=i*70 + 80 - 50 % i;
                    break;
                case 1:
                    j=(i-150)*6 - 330 ;
                    break;
                case 2:
                    j=i%13 + (i-22)*5 + 912;
                    break;
                case 3:
                    j=(i+20)*4 -i%3 -11;
                    break;
            }
            firstScrambledArray[i-1]=j;
            secondScrambledArray[250-i]=j;
        }
        DHeap.DHeapSort(firstScrambledArray,5);
        DHeap.DHeapSort(secondScrambledArray,6);

        boolean isFirstSorted=true;
        boolean isSecondSorted=true;
        for(int i=1;i<=249;i++)
        {
            if(firstScrambledArray[i-1]>firstScrambledArray[i])
            {
                isFirstSorted=false;
            }
            if(secondScrambledArray[i-1]>secondScrambledArray[i])
            {
                isSecondSorted=false;
            }

        }
        count2BooleansAsTest(isFirstSorted,isSecondSorted);
        System.out.println("Passed tests="+Integer.toString(passedTests)+ ",failed tests="+Integer.toString(failedTests));

    }

    private static void count2BooleansAsTest(boolean firstBoolean,
                                             boolean secondBoolean) {
        if(firstBoolean)
        {
            passedTests++;
        }
        else
        {
            failedTests++;
        }
        if(secondBoolean)
        {
            passedTests++;
        }
        else
        {
            failedTests++;
        }

    }

    private static void performMinSizeAndIsHeapTest(DHeap heap13, int expectedSize, int expectedMin,
                                                    boolean expectedIsHeap) {
        int heapMin=heap13.Get_Min().getKey();

        System.out.println("performMinSizeAndIsHeapTest");
        System.out.println("ES: " + expectedSize + "AS: " + heap13.getSize());
        System.out.println("EM: " + expectedMin + "AS: " + heapMin);
        try {
            if(heap13.getSize() == expectedSize && heapMin == expectedMin && heap13.isHeap() == expectedIsHeap)
            {
                passedTests++;
            } else {
                failedTests++;
            }
        }
        catch (Exception e)
        {
            failedTests++;
        }


    }

    private static void performMinAndSizeTest(DHeap heap4, DHeap heap7, int expectedMin,
                                              int expectedSize) {

        int firstHeapMin=heap4.Get_Min().getKey();
        int secondHeapMin=heap7.Get_Min().getKey();
        System.out.println("performMinAndSizeTest");
        System.out.println("ES: " + expectedSize + "AS: " + heap4.getSize() + " " + heap7.getSize());
        System.out.println("EM: " + expectedMin + "AS: " + firstHeapMin + " " + secondHeapMin);
        if(firstHeapMin == expectedMin && heap4.getSize() == expectedSize)
        {
            passedTests++;
        }
        else
        {
            failedTests++;
        }
        if(secondHeapMin == expectedMin && heap7.getSize() == expectedSize)
        {
            passedTests++;
        }
        else
        {
            failedTests++;
        }
    }

    private static void performSanityTest(DHeap Heap4, DHeap Heap7) {
        try{
            if(Heap4.getSize() == 0 && Heap4.isHeap())
            {
                passedTests++;
            }
            else
            {
                failedTests++;
            }
        } catch (Exception e) {
            failedTests++;
        }

        try{
            if(Heap7.getSize() == 0 && Heap7.isHeap())
            {
                passedTests++;
            }
            else
            {
                failedTests++;
            }
        }
        catch (Exception e){
            failedTests++;
        }

    }



}