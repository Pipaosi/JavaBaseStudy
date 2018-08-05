package collection;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap 多线程不安全死循环测试
 * 
 * 【
 * 		参考hashmap在并发场景下的死循环问题：https://coolshell.cn/articles/9606.html
 * 		当 多个线程在进行rehash的时候，会同时操作一个共同的存储数据，涉及到链表数据迁移的时候，会造成环形链表的结构。（get和put操作线程不安全，但是最多只会丢失数据，不会构成死循环）
 * 	然后get数据（或put 因为put也会遍历链表），遍历到这个环形链表的时候，就会出现死循环。导致CPU 100%，可以在linux下top看到对应CPU100%线程号，然后jstack查看对应线程，
 * 	会发现线程卡在HashMap的get方法处
 * 】
 * 
 * @author yangxli
 * @date 2018年8月2日 下午9:42:36
 */
public class HashMapTest {
	public static void main(String[] args) {
			
		concurrentHashMapTest();
		
		hashMapTest();
		
		//对别可以看出来，concurrenthashmap每次都是输出固定值，而hashmap存在多次运行数据不一致的情况，并且会卡在指定key的get操作中
	}

	/**
	 * hashMap的死循环并发测试
	 * 
	 * @param
	 * @return 
	 * @throws 
	 */
	private static void hashMapTest() {
		//两个生产者往hashmap中put数据
		HashMap<Integer, Object> map = new HashMap<>();
		ProducerPutData producerPutData1 = new ProducerPutData(map);
		ProducerPutData producerPutData2 = new ProducerPutData(map);
		new Thread(producerPutData1).start();
		new Thread(producerPutData2).start();
		
		
		//主线程get 数据，get可能会出现死循环
		for (int i = 0; i < 1_0000; i++) {
//			if (map.containsKey(i)) {
				Object data = map.get(i);
				System.out.println("i:" + i + " data:" + data);
//			}
		}
	}
	
	static class ProducerPutData implements Runnable{
		private HashMap<Integer, Object> map;

		public ProducerPutData(HashMap<Integer, Object> map) {
			super();
			this.map = map;
		}

		@Override
		public void run() {
			Random random = new Random();
			while(true) {
				map.put(random.nextInt()% (1024 * 1024 * 100), null);
			}
		}
	}

	/**
	 * concurrentHashMap 会发现并发下没有改问题
	 * 
	 * @param
	 * @return 
	 * @throws 
	 */
	private static void concurrentHashMapTest() {
		//两个生产者往hashmap中put数据
		ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<>();
		ConcurrentProducerPutData producerPutData1 = new ConcurrentProducerPutData(map);
		ConcurrentProducerPutData producerPutData2 = new ConcurrentProducerPutData(map);
		new Thread(producerPutData1).start();
		new Thread(producerPutData2).start();
		
		
		//主线程get 数据，get可能会出现死循环
		for (int i = 0; i < 1_0000; i++) {
//			if (map.containsKey(i)) {
				Object data = map.get(i);
				System.out.println("i:" + i + " data:" + data);
//			}
		}
	}

	static class ConcurrentProducerPutData implements Runnable{
		private ConcurrentHashMap<Integer, Object> map;

		public ConcurrentProducerPutData(ConcurrentHashMap<Integer, Object> map) {
			super();
			this.map = map;
		}

		@Override
		public void run() {
			Random random = new Random();
			int i = 0;
			while(i < 1_0000) {
				i++;
				map.put(random.nextInt()% (1024 * 1024 * 100), 1);
			}
		}
	}
}