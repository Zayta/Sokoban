package exp.zhen.zayta.util;

import java.util.ArrayList;
import java.util.Set;

public class KeyListMap<K,V>{
    private BiMap<K, ArrayList<V>> biMap = new BiMap<K,ArrayList<V>>();

    public void put(K k, V v) {
        ArrayList<V> list = biMap.get(k);
        if(list==null){
            list = new ArrayList<V>();
            biMap.put(k,list);
        }
        list.add(v);
    }

    public ArrayList<V> get(K k) {
        return biMap.get(k);
    }

    public int size()
    {
        return biMap.size();
    }

    public Set<K> keySet()
    {
        return biMap.keySet();
    }

    public void clear()
    {
        biMap.clear();
    }

    public ArrayList<V> remove(Object key)
    {
        ArrayList<V> ret = biMap.remove(key);
        return ret;
    }
    public boolean containsKey(Object key)
    {
        return biMap.containsKey(key);
    }
    public boolean containsValue(Object value)
    {
        return biMap.containsValue(value);
    }
}
