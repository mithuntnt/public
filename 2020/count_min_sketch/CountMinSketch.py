

def to_integer(byte_word, n_bits):
    # https://stackoverflow.com/questions/47086683/how-to-turn-the-first-n-bits-of-a-digest-into-a-integer
    n_bytes = (n_bits + 7) // 8
    shift_right = 8 - n_bits % 8

    if n_bytes > len(byte_word):
        raise ValueError("Require {} bytes, received {}".format(n_bytes, len(byte_word)))

    i = int.from_bytes(byte_word[:n_bytes], 'big')

    if shift_right:
        i >>= shift_right

    return i

class CountMinSketch:
    hash_funcs = []
    output_bits = None
    n_output = None

    count_map = []

    @staticmethod
    def get_hash_func(output_bits, salt=b'', algorithm_name='sha256'):
        import hashlib

        def t(b):
            m = hashlib.new(algorithm_name)

            m.update(salt)
            m.update(b)

            return to_integer(m.digest(), output_bits)

        return t


    def __init__(self, num_hash_func=4, output_bits=3):
        self.reset(num_hash_func, output_bits)

    def reset(self, num_hash_func=4, output_bits=3):
        self.output_bits = output_bits
        self.n_output = 2 ** output_bits
        self.count_map = []
        self.hash_funcs = []

        for i in range(num_hash_func):
            self.hash_funcs.append(CountMinSketch.get_hash_func(output_bits, bytes([i])))
            self.count_map.append([0] * self.n_output)

    def push(self, byte_word):
        for i, func in enumerate(self.hash_funcs):
            v = func(byte_word)

            self.count_map[i][v] += 1

    def get_count(self, byte_word):
        counts = []

        for i, func in enumerate(self.hash_funcs):
            v = func(byte_word)
            counts.append(self.count_map[i][v])

        return min(counts)

    def print_counts(self):
        for i, counts in enumerate(self.count_map):
            print(i, end=' ')
            print(counts, flush=True)


