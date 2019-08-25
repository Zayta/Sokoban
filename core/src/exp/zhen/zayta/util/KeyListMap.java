package exp.zhen.zayta.util;

import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class KeyListMap<K,V>{
    private static final Logger log = new Logger(KeyListMap.class.getName(),Logger.DEBUG);
    /*
    * K = Key = PositionTracker position key. Each key K is associated with a list, which contains object V
    * List = list of V
    * V = object stored in list
    * */
    private BiMap<K, ArrayList<V>> keyList = new BiMap<K,ArrayList<V>>();
    private BiMap<V,K> objKeys = new BiMap<V, K>();

    public void put(K k, V v) {
        ArrayList<V> list = keyList.get(k);
        if(list==null){
            list = new ArrayList<V>();
            keyList.put(k,list);
        }
        list.add(v);

        objKeys.put(v,k);
    }
//    public V remove(K key)
//    {
//        V removedObj = objKeys.getKey(key);
//
//        if(removedObj!=null&&keyList.get(key)!=null)
//        keyList.get(key).remove(removedObj);
//        return removedObj;
//    }
//
//    //
//    public K removeKey(V value)
//    {
//        //what if there are several keys that map to the same value?
//        K removedKey = objKeys.remove(value);
//        if(removedKey!=null&&keyList.get(removedKey)!=null)
//            keyList.get(removedKey).remove(value);
//        return removedKey;
//    }
    public boolean remove(K key, V value){
        if(objKeys.containsKey(value)&&objKeys.get(value)==key){//if the value is there and it is hashed to the indicated key
            objKeys.removeKey(value);

            keyList.get(key).remove(value);

            return true;
        }

        return false;
    }
    public K remove(V value)
    {
        K key = objKeys.remove(value);
        if(key.getClass().equals(value.getClass())){
            log.debug("KeyListMap classes the same. Are you sure you want to use this method?");
        }
        if(keyList.get(key)!=null)
            keyList.get(key).remove(value);
        return key;
    }

    public K removeKeyAndList(V value){
        return keyList.removeKey(removeKeyAndList(value));
    }

    public ArrayList<V> getList(K k) {
        return keyList.get(k);
    }
    public K getKey(V v) {
        return objKeys.get(v);
    }

    public V get(K k){
        return objKeys.getKey(k);
    }

    public int keyListSize()
    {
        return keyList.size();
    }
    public int numObjects(){
        return objKeys.size();//this is different from keyListSize because it lists out distinct items. Keylist lists number of lists
    }

    public Set<K> keySet()
    {
        return keyList.keySet();
    }

    public Set<V> objects()
    {
        return objKeys.keySet();
    }

    public void clear()
    {
        keyList.clear();
        objKeys.clear();
    }

    @Override
    public String toString() {
        String ret = "";

        ret+=super.toString();
        for(K key: keySet()){
            ret+="\nKey: "+key+" has"+keyList.get(key).size() +
                    "values: "+Arrays.toString(keyList.get(key).toArray());

        }

        return ret;
    }
}
