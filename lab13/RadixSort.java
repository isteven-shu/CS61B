/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        String[] sorted = new String[asciis.length];
        System.arraycopy(asciis, 0, sorted, 0, asciis.length);

        int W = 0;
        for (String str : asciis) {
            W = W < str.length() ? str.length() : W;
        }

        for (int i = W - 1; i >= 0; --i) {
            sortHelperLSD(sorted, i);
        }

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        /** Create and initialize the counts array. */
        int[] counts = new int[256];
        for (String str : asciis) {
            if (index < str.length()) {
                counts[(int)(str.charAt(index))]++;
            } else {
                counts[0]++;
            }
        }

        /** Create and initialize the starts array. */
        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < 256; ++i) {
            starts[i] = pos;
            pos += counts[i];
        }

        /** Do the sort one by one. */
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; ++i) {
            if (index < asciis[i].length()) {
                sorted[starts[(int)(asciis[i].charAt(index))]] = asciis[i];
                starts[(int)(asciis[i].charAt(index))]++;
            } else {
                sorted[starts[0]] = asciis[i];
                starts[0]++;
            }

        }

        /** Copy back. */
        for (int i = 0; i < asciis.length; ++i) {
            asciis[i] = sorted[i];
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args){
        String[] a = new String[]{ "Gotengco", "Lira","Guban", "Garraez", "Liam","Noah","William","James"
                ,"Logan","Benjamin","Mason","Elijah","Oliver","Jacob","Lucas","Michael","Alexander","Ethan"
                ,"Daniel","Matthew","Aiden","Henry","Joseph","Jackson","Samuel","Sebastian","David","Carter",
                "Wyatt","Jayden","John","Owen","Dylan","Luke","Gabriel","Anthony","Isaac","Grayson","Jack"
                ,"Julian","Levi","Christopher","Joshua","Andrew","Lincoln","Mateo","Ryan","Jaxon","Nathan",
                "Aaron","Isaiah","Thomas","Charles","Caleb","Josiah","Christian","Hunter","Eli","Jonathan",
                "Connor","Landon","Adrian","Asher","Cameron","Leo","Theodore","Jeremiah","Hudson","Robert",
                "Easton","Nolan","Nicholas","Ezra","Colton" };
        String[] b;
        b = sort(a);
        for(String s : b){
            System.out.println(s);
        }
    }
}
