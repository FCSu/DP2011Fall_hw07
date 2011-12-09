import java.util.*;
public class Main {
    public static void main(String[] s) {
        Out.write(HelloWorld.map.get(1));
    }
}
class HelloWorld {
    public static HashMap<Integer,String> map;
    static {
	map = new HashMap<Integer,String>();
	map.put(1, "Hi");
	map.put(2, "Hello");
    }
}
