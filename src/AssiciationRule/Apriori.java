package AssiciationRule;

import Library.GeneralConstant;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dark Wolf on 02/22/2017.
 */
public class Apriori {
    private String[] stringArrayList;
    private double minimumSupport;
    private int recordsLength;
    private int maximumLengthOfKey;
    // Main variable of function
    private Map<String, Integer> map = new Map<String, Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Integer get(Object key) {
            return null;
        }

        @Override
        public Integer put(String key, Integer value) {
            return null;
        }

        @Override
        public Integer remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends Integer> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return null;
        }

        @Override
        public Collection<Integer> values() {
            return null;
        }

        @Override
        public Set<Entry<String, Integer>> entrySet() {
            return null;
        }
    };


    /**
     * GET LIST VARIABLES TO MAP
     *
     * @param records
     * @return
     */
    public int getListVariables(String[] records) {
        int result = GeneralConstant.RESULT_FAIL;
        for (String record : records) {
            // get the largest Length of key
            if (record.length() > this.maximumLengthOfKey) this.maximumLengthOfKey = record.length();

            String[] string = record.split("-");
            for (String key : string) {
                this.map.putIfAbsent(key, GeneralConstant.DEFAULT_VALUE_MAP); // If not existed in map, add it
            }
        }
        result = GeneralConstant.RESULT_SUCCESS;
        return result;
    }

    /**
     * Constructor
     *
     * @param stringArrayList
     * @param minimumSupport
     */
    public Apriori(String[] stringArrayList, double minimumSupport) {
        this.stringArrayList = stringArrayList;
        this.minimumSupport = minimumSupport;
        this.recordsLength = stringArrayList.length;
        // If records list is too short
        if (recordsLength == 1) return;
        this.maximumLengthOfKey = 0;

        if (getListVariables(stringArrayList) == GeneralConstant.RESULT_FAIL) {
            return;
        }

        String[] keyArray = this.map.keySet().toArray(new String[this.map.keySet().size()]);
        // if exist -> plus 1
        for (String record : this.stringArrayList) {
            for (String key : keyArray) {
                if (record.contains(key)) {
                    this.map.replace(key, this.map.get(key) + 1);
                }
            }
        }
        // Remove key have value lower than minimumSupport
        for (String key : keyArray) {
            if (this.map.get(key) / this.minimumSupport < this.minimumSupport) {
                this.map.remove(key);
            }
        }

        // Make the next level of key
        for (int position = 0; position < keyArray.length - 1; position++) {
            for (int nextPosition = position + 1; nextPosition < keyArray.length; nextPosition++) {
                map.putIfAbsent(keyArray[position] + keyArray[nextPosition], GeneralConstant.DEFAULT_VALUE_MAP);
            }
        }

        // Remove key have value lower than minimumSupport
        for (String key : keyArray) {
            if (this.map.get(key) / this.minimumSupport < this.minimumSupport) {
                this.map.remove(key);
            }
        }
    }
}
