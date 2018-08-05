package collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author yangxli
 * @date 2018年7月24日 下午4:48:12
 * 
 * 	【
 * 		集合基本操作实例
 * 	】
 */
public class Collection {
	public static void main(String[] args) {
		setTest();		
		System.out.println(indexFor(6,3));

	}
	
	/**
	 * HashMap源码计算key对应HASH值应放到数组哪个位置
	 * 	【这种取模方法是基于hash数组大小(length)总是2的N次方】
	 * 
	 * @param
	 * @return 
	 * @throws 
	 */
	static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        return h & (length-1);
    }
	
	/**
	 * 测试Set是乱序且不能重复的集合
	 * 
	 * @param
	 * @return 
	 * @throws 
	 */
	public static void setTest() {
        Set<String> set = new HashSet<>();
        set.add("11111");
        set.add("22222");
        set.add("33333");
        set.add("22222");
        set.add("44444");

        System.out.println(set.size() + set.toString());
        for (String e : set) {
            System.out.println(e);
        }
    }
	
	/**
	 * Map遍历方法
	 * 
	 * @param
	 * @return 
	 * @throws 
	 */
	public static void mapTest() {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("1", "value1");
	      map.put("2", "value2");
	      map.put("3", "value3");
	      
	      //第一种：普遍使用，二次取值
	      System.out.println("通过Map.keySet遍历key和value：");
	      for (String key : map.keySet()) {
	    	  System.out.println("key= "+ key + " and value= " + map.get(key));
	      }
	      
	      //第二种
	      System.out.println("通过Map.entrySet使用iterator遍历key和value：");
	      Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
	      while (it.hasNext()) {
	    	  Map.Entry<String, String> entry = it.next();
	    	  System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	      }
	      
	      //第三种：推荐，尤其是容量大时
	      System.out.println("通过Map.entrySet遍历key和value");
	      for (Map.Entry<String, String> entry : map.entrySet()) {
	    	  System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	      }
	    
	      //第四种
	      System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
	      for (String v : map.values()) {
	    	  System.out.println("value= " + v);
	      }
	}
}
