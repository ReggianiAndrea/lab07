package it.unibo.inner.api;

import java.util.Iterator;

public class GenericPourpose<T> implements IterableWithPolicy<T>{

    private T[] array;
    private Predicate<T> predicate;
    /* 
    dichiarazione Predicate --> è un interfaccai generica di Java 
    rappresenta un test/condizione su elemento
    */


    /*2-ary constructor */
    public GenericPourpose(final T array[], Predicate<T> predicate){
        this.array=array;
        this.predicate=predicate;
    }

    /*1-ary constructor--> modified for step 2 */
    public GenericPourpose(final T[] array){
        //this.array=array;
        this(array,new Predicate<T>() {
            @Override
            public boolean test(T element){
            return true;
            }
        }   );
    }


    @Override
    public Iterator<T> iterator(){
        return new InnerClass();
    }

    /*
    before was empty--< modified for step 2
    set the value of predicate 
     */
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate=filter;
    }

    /*
    declaration of the inner class "InnerClass"
    that implements Iterator
     */
    public class InnerClass implements Iterator<T>{

        private int position=0;

        @Override
        public boolean hasNext() {

             while(position < array.length){
                if(predicate.test(array[position])){
                    return true;
                }
                position++;
             }
             return false;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new java.util.NoSuchElementException();
            }
                return array[position++];
            
        }
    }
}
