package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;

    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        if (++state == period) {
            state = 0;
        }
        return normalize();
    }

    private double normalize() {
        return -1.0 + (2.0 / period) * state;
    }
}
