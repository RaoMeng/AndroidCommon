package com.raomeng.common.rx;

/**
 * @author RaoMeng
 * @describe RxBus事件实体类
 * @date 2018/1/11 10:21
 */

public class RxEvent<T> {
    private int flag;
    private T data;

    private RxEvent(Builder<T> builder) {
        flag = builder.flag;
        data = builder.data;
    }

    public int getFlag() {
        return flag;
    }

    public T getData() {
        return data;
    }

    /**
     * {@code RxEvent} builder static inner class.
     */
    public static final class Builder<T> {
        private int flag;
        private T data;

        public Builder() {
        }

        /**
         * Sets the {@code flag} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param flag the {@code flag} to set
         * @return a reference to this Builder
         */
        public Builder flag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * Sets the {@code data} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param data the {@code data} to set
         * @return a reference to this Builder
         */
        public Builder data(T data) {
            this.data = data;
            return this;
        }

        /**
         * Returns a {@code RxEvent} built from the parameters previously set.
         *
         * @return a {@code RxEvent} built with parameters of this {@code RxEvent.Builder}
         */
        public RxEvent build() {
            return new RxEvent(this);
        }
    }
}
