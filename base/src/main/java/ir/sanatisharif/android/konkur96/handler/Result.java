package ir.sanatisharif.android.konkur96.handler;

import java.util.function.Supplier;

public abstract class Result<V, E> {
    
    private Result() {
    }
    
    abstract V success(Supplier<V> other);
    
    abstract E error(Supplier<E> other);
    
    public final static class Success<V, E> extends Result<V, E> {
        
        public final V value;
        
        public Success(V value) {
            this.value = value;
        }
        
        @Override
        V success(Supplier<V> other) {
            return value;
        }
        
        @Override
        E error(Supplier<E> other) {
            return null;
        }
    }
    
    public final static class Error<V, E> extends Result<V, E> {
        
        public final E value;
        
        public Error(E value) {
            this.value = value;
        }
        
        @Override
        V success(Supplier<V> other) {
            return null;
        }
        
        @Override
        E error(Supplier<E> other) {
            return value;
        }
    }
}
