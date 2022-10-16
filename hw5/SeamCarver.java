import java.awt.*;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture myPicture;
    private boolean transpose;
    public SeamCarver(Picture picture) {
        myPicture = new Picture(picture);   // Copy Construction.
        transpose = false;
    }

    /** Using the copy constructor for Picture. */
    public Picture picture() {
        return new Picture(myPicture);
    }

    public int width() {
        if (transpose)  return myPicture.height();
        return myPicture.width();
    }

    public int height() {
        if (transpose)  return myPicture.width();
        return myPicture.height();
    }

    /** These four functions only apply to the original picture and must not be affected
     *  by the transposition. */
    private int right(int x) {
        if (x + 1 == myPicture.width()) {
            return 0;
        }
        return x + 1;
    }
    private int left(int x) {
        if (x == 0) {
            return myPicture.width() - 1;
        }
        return x - 1;
    }
    private int up(int y) {
        if (y == 0) {
            return myPicture.height() - 1;
        }
        return y - 1;
    }
    private int down(int y) {
        if (y + 1 == myPicture.height()) {
            return 0;
        }
        return y + 1;
    }

    public double energy(int x, int y) {
        Color right = myPicture.get(right(x), y);
        Color left = myPicture.get(left(x), y);
        Color up = myPicture.get(x, up(y));
        Color down = myPicture.get(x, down(y));

        double rx = right.getRed()   -  left.getRed();
        double gx = right.getGreen() -  left.getGreen();
        double bx = right.getBlue()  -  left.getBlue();
        double ry = up.getRed()      -  down.getRed();
        double gy = up.getGreen()    -  down.getGreen();
        double by = up.getBlue()     -  down.getBlue();

        return rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by;
    }

    public int[] findHorizontalSeam() {
        if (height() == 1)   return new int[width()];
        transpose = true;
        int[] seam = findVerticalSeam();
        transpose = false;
        return seam;
    }

    public int[] findVerticalSeam() {
        if (width() == 1)   return new int[height()];

        int[] seam = new int[height()];  // The answer.
        double[][] M = new double[height()][width()];   // The array for dp.
        double[][] e = new double[height()][width()];   // The array to record energy.

        // Initiate array e.
        for (int j = 0; j < height(); ++j) {
            for (int i = 0; i < width(); ++i) {
                e[j][i] = (!transpose)? energy(i, j) : energy(j, i);
            }
        }

        // Calculate array M.
        for (int i = 0; i < width(); ++i) {
            M[0][i] = e[0][i];
        }
        for (int j = 1; j < height(); ++j) {
            for (int i = 0; i < width(); ++i) {
                if (i == 0) {
                    M[j][i] = e[j][i] + Math.min(M[j - 1][i], M[j - 1][i + 1]);
                } else if (i == width() - 1) {
                    M[j][i] = e[j][i] + Math.min(M[j - 1][i], M[j - 1][i - 1]);
                } else {
                    M[j][i] = e[j][i] + Math.min(Math.min(M[j - 1][i], M[j - 1][i - 1]), M[j - 1][i + 1]);
                }
            }
        }

        // Calculate seam.
        int minIdx = 0; // The index of the smallest item at bottom row.
        for (int i = 0; i < width(); ++i) {
            if (M[height() - 1][i] < M[height() - 1][minIdx]) {
                minIdx = i;
            }
        }
        seam[height() - 1] = minIdx;
        for (int j = height() - 2; j >= 0; --j) {
            for (int i = minIdx - 1; i <= minIdx + 1; ++i) {
                if (i < 0 || i == width()) {
                    continue;
                }
                if (M[j][i] == M[j + 1][minIdx] - e[j + 1][minIdx]) {
                    minIdx = i;
                    seam[j] = i;
                    break;
                }
            }
        }
        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        myPicture = SeamRemover.removeHorizontalSeam(myPicture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        myPicture = SeamRemover.removeVerticalSeam(myPicture, seam);
    }
}
