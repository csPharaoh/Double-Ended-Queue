public class ArrayDeque<T> {
    private T [] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private int nFcounter;
    private int nLcounter;
   
    public ArrayDeque() {
        items = (T[]) new Object[4];
        nextFirst = 1;        
        nextLast = 2;       
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {     
        T [] copy = (T []) new Object [other.items.length]; 
        
        System.arraycopy(other.items, 0, copy, 0, other.items.length);        
        items = copy; 
          
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;
        nFcounter = other.nFcounter;
        nLcounter = other.nLcounter;
        
    }

    public void addFirst(T x) {
        if (isFull()) {
            
            resizeUp(size * 2);
                        
            items[nextFirst] = x;
            nFcounter = nFcounter + 1;
            size = size + 1;
            nextFirst = nextFirst - 1;
            if (nextFirst == 0) {
                nextFirst = items.length - 1;
            }
        } else {
            items[nextFirst] = x;
            nFcounter = nFcounter + 1;
            size = size + 1;
            nextFirst = nextFirst - 1;
        
            if (nextFirst == -1) {
                nextFirst = items.length - 1;
            }
        }   
    }

    public void addLast(T x) {  
        if (isFull()) {
            
            resizeUp(size * 2);
            
            items[nextLast] = x;
            size = size + 1;
            nextLast = nextLast + 1;
            nLcounter = nLcounter + 1;
            if (nextLast == items.length) {
                nextLast = 0;
            }
        } else {
            items[nextLast] = x;        
            nLcounter = nLcounter + 1;
            size = size + 1;
            nextLast = nextLast + 1;       
            if (nextLast == items.length) {
                nextLast = 0;
            }
        }   
    }
    
    private void resizeUp(int capacity) {
        T [] carrier = items;
        T [] temp = (T []) new Object [capacity]; 
        arrCopy(items, temp, false); //copy Lasts
        arrCopy(carrier, temp, true); //copy firsts
        
        nextFirst = (items.length - 1) - nFcounter;
        nextLast = nLcounter; 
    }
    
    private void resizeDown(int capacity) {
        T [] carrier = items;
        T [] temp = (T []) new Object [capacity]; 
        arrCopy(items, temp, false); //copy Lasts
        arrCopy(carrier, temp, true); //copy firsts
              
        nextFirst = (items.length - 1) - nFcounter;
        nextLast = nLcounter;     
    }
       
    public boolean isEmpty() {
        if (size() == 0) {
            return  true;
        }
        return  false;
    }
    
    private void printAll() {
        for (T element : this.items) {
            System.out.print(element + " ");
        }
    }
    public void printDeque() {    
        
        int i = 0;
        while (i < size) {
         
            System.out.print(get(i));
            i = i + 1;
        }
        System.out.println("");
        
    }
    
    public T get(int i) {
        return items[(nextFirst + 1 + i) % (items.length)];
    }
    
    public int size() {
        return size;
    }
     
    private float useRatio() {
        return ((float) size / items.length) * 100;
    }
    
    
    public T removeFirst() {  
        if (isEmpty()) {
            return null;
        }
        
        T returned = items[(nextFirst + 1) % items.length];
        
        size = size - 1;
        
        items[(nextFirst + 1) % items.length] = null;       
        nextFirst = (nextFirst + 1) % items.length;
        
        if (nFcounter > 0) {
            nFcounter = nFcounter - 1; 
        } else {
            nLcounter = nLcounter - 1;
        }
               
        if (useRatio() < 25.0 && useRatio() > 0) {
            resizeDown(items.length / 2);
        }
        
        return returned;
    }
    
    public T removeLast() {        
        if (isEmpty()) {
            return null;
        }
        
        T returned = items[Math.floorMod(nextLast - 1, items.length)];
        size = size - 1; 
        
        
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        
        nextLast = Math.floorMod(nextLast - 1, items.length);           
             
        if (nLcounter > 0) {
            nLcounter = nLcounter - 1; 
        } else {
            nFcounter = nFcounter - 1; 
        }
        
        if (useRatio() < 25.0 && useRatio() > 0) {
            resizeDown(items.length / 2);  
        }
                   
        return returned;
    }
    
    private boolean isFull() {
        if (size == items.length) {
            return true;
        }
        return false;
    }
    
    private void arrCopy(T[] source, T[] dest, boolean flag) {
        int nFindex = 0;
        int nLindex = 0;
        int nLmod = Math.floorMod(nextLast - 1, source.length); 
        int nFtemp = Math.floorMod(nextFirst + 1, source.length);
        int len = source.length;
        
        if (flag) {             
            for (int x = 0; x < nLcounter; x++) {
 
                dest[nLcounter - x - 1] = source[Math.floorMod((nLmod - x), len)];
            } 
        } else {             
            for (int x = 0; x < nFcounter; x++) {
                    
                dest[dest.length - nFcounter + x] = source[Math.floorMod((nFtemp + x), len)];
            }
        }
        
        items = dest;  
        
    }
}
