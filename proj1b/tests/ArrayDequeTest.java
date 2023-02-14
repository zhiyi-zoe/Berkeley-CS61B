import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    public void addFirstTestBasic() {
        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

    }

    @Test
    public void addLastTestBasic() {
        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    @Test
    /** This test performs isEmpty calls. */
    public void isEmptyTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> ad2 = new ArrayDeque<>();

        assertThat(ad1.isEmpty()).isTrue();

        ad2.addLast(1);
        assertThat(ad2.isEmpty()).isFalse();

        ad1.addFirst(2);
        assertThat(ad1.isEmpty()).isFalse();
    }

    @Test
    /** This test performs size calls. */
    public void sizeTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> ad2 = new ArrayDeque<>();

        assertThat(ad1.size()).isEqualTo(0);

        ad1.addFirst(1);
        assertThat(ad1.size()).isEqualTo(1);

        ad2.addLast(3);
        assertThat(ad2.size()).isEqualTo(1);

        ad1.addLast(4);
        ad1.addLast(2);
        ad1.addFirst(5);
        assertThat(ad1.size()).isEqualTo(4);
    }

    @Test
    /** This test performs get calls. */
    public void getTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();

        ad1.addFirst(3);
        assertThat(ad1.get(28976)).isNull();
        assertThat(ad1.get(-7)).isNull();
        assertThat(ad1.get(0)).isEqualTo(3);

        ad1.addLast(2);
        ad1.addLast(4);
        ad1.addLast(6);
        assertThat(ad1.get(3)).isEqualTo(6);
        assertThat(ad1.get(4)).isNull();
    }
    @Test
    public void removeFirstAndRemoveLastTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.removeLast()).isEqualTo(2); // [-2, -1, 0, 1]
        ad1.removeLast(); // [-2, -1, 0]
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        assertThat(ad1.toList()).containsExactly(-2, -1, 0).inOrder();

        assertThat(ad1.removeFirst()).isEqualTo(-2); // [-1, 0]
        ad1.removeFirst(); // [0]
        ad1.addLast(3); // [0, 3]
        assertThat(ad1.toList()).containsExactly(0, 3).inOrder();
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);

        ad1.removeFirst(); // [3]
        ad1.removeLast(); // null
        assertThat(ad1.toList()).isEmpty();
        assertThat(ad1.removeFirst()).isNull();
        assertThat(ad1.toList()).isEmpty();

        ad1.addFirst(1); // [1]
        ad1.addFirst(2); // [2, 1]
        assertThat(ad1.removeFirst()).isEqualTo(2); // [1]
        ad1.removeFirst(); // null
        assertThat(ad1.toList()).isEmpty();
        assertThat(ad1.size()).isEqualTo(0);
        ad1.addLast(9);
        assertThat(ad1.toList()).containsExactly(9).inOrder();
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        ad1.addLast(10);
        ad1.addLast(11);
        assertThat(ad1.removeLast()).isEqualTo(11);
        assertThat(ad1.removeLast()).isEqualTo(10);
        assertThat(ad1.toList()).containsExactly(9).inOrder();

    }

    @Test
    public void resizingDown() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            ad1.addLast(5);
        }

        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);

        for (int j = 0; j < 7500; j++) {
            ad1.removeFirst();
        }

        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        ad1.removeFirst();
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        for (int j = 0; j < 2499; j++) {
            ad1.removeFirst();
        }
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);

        for (int i = 0; i < 10000; i++) {
            ad1.addFirst(5);
        }

        for (int j = 0; j < 7500; j++) {
            ad1.removeLast();
        }
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        ad1.removeLast();
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);
        for (int j = 0; j < 2499; j++) {
            ad1.removeLast();
        }
        assertThat(((ArrayDeque<Integer>) ad1).usageFactor()).isEqualTo(true);

    }

}
