from random import choice
import unittest
from CountMinSketch import CountMinSketch


class CountMinSketchTests(unittest.TestCase):
    dictionary = [b'one', b'two', b'three', b'four', b'five', b'six', b'seven', b'eight', b'nine', b'ten']


    def generic(self, num_hash_func, n_bits, byte_words, expected_counts, compare='exact'):
        cms = CountMinSketch(num_hash_func, n_bits)

        for byte_word in byte_words:
            cms.push(byte_word)

        cms.print_counts()

        for byte_word, expected_count in expected_counts.items():
            found_count = cms.get_count(byte_word)

            if compare == 'exact':
                self.assertEqual(found_count, expected_count, byte_word)
            if compare == 'gte':
                self.assertGreaterEqual(found_count, expected_count, byte_word)

    def test_something(self):
        self.assertEqual(True, True)

    def test_001(self):
        self.generic(4, 4, [b'One', b'Two', b'Three', b'Four'], {
            b'One': 1, b'Two': 1, b'Three': 1, b'Four': 1
        })

    def test_002(self):
        self.generic(4, 3, [b'One', b'Two', b'Three', b'Four'], {
            b'One': 1, b'Two': 1, b'Three': 1, b'Four': 1
        })

    def test_003(self):
        words = [choice(self.dictionary) for _ in range(100) ]
        word_counts = {}

        print(words)

        for word in words:
            if word in word_counts:
                word_counts[word] += 1
            else:
                word_counts[word] = 1

        self.generic(4, 5, words, word_counts)

    def test_004(self):
        words = [choice(self.dictionary) for _ in range(100) ]
        word_counts = {}

        print(words)

        for word in words:
            if word in word_counts:
                word_counts[word] += 1
            else:
                word_counts[word] = 1

        self.generic(4, 4, words, word_counts, 'gte')


if __name__ == '__main__':
    unittest.main()
