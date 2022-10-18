package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;

    private double factor;

    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.factor = factor;
        this.period = period;
    }

    @Override
    public double next() {
        if (++state == period) {
            state = 0;
            period *= factor;
        }
        return normalize();
    }

    private double normalize() {
        return -1.0 + (2.0 / period) * state;
    }
}
