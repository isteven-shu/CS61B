import synthesizer.GuitarString;

/** 被注释的也可以，声音是会衰减的，每次按下按键，只有被按下的弦的array不为0，循环加一边可以保证获取到那个被按下的弦，但是费时间
 *  不循环加，需要多一个if，保证不会有小于0的index被索引 */
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] guitar = new GuitarString[37];
        for (int i = 0; i < guitar.length; ++i) {
            guitar[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
        }
        int index = 0;
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                index = keyboard.indexOf(key);
                if (index < 0) {
                    continue;
                }
                guitar[index].pluck();
            }
            if (index < 0) {
                continue;
            }
            /* compute the superposition of samples */
//            double sample = 0;
//            for (int i = 0; i < guitar.length; ++i) {
//                sample += guitar[i].sample();
//            }
            double sample = guitar[index].sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
//            for (int i = 0; i < guitar.length; ++i) {
//                guitar[i].tic();
//            }
            guitar[index].tic();
        }
    }
}
