/**
 * 只有复杂的系统级程序才有可能有死锁的存在
 * 多数用于onlineshiping
 *flag=1
 *flag=0
 * DeadLocking...
 */
public class TestDeadLock implements Runnable{
    public int flag =1;
    static Object o1 = new Object();
    static Object o2 = new Object();
    public void run(){
        System.out.println("flag="+flag);
        if(flag == 1){ //////111111
            synchronized (o1){//先锁住了o1
                try{
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (o2){
                    System.out.println("1");
                }
            }
        }

        if(flag == 0) {///////000000
            synchronized (o2) { ////先锁住了o2
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {  //只要再锁住o1
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String[] args){
        TestDeadLock td1 = new TestDeadLock();
        TestDeadLock td2 = new TestDeadLock();
        td1.flag = 1;//设置td1的 member variable 值为1
        td1.flag = 0;//设置td1的 member variable 值为0
        Thread t1 = new Thread(td1);
        Thread t2 = new Thread(td2);
        t1.start();//几乎同时启动t1 t2
        t2.start();
    }
}
