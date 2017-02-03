import unittest
from collections import Counter
import similarity as sim

class TestSimilarityMetrics(unittest.TestCase):

    def test_counter(self):
        c1 = Counter({'x':1})
        c2 = Counter({'x':1})
        c3 = Counter({'y':1})
        c4 = Counter({'x':3, 'y':4})
        self.assertEqual(sim.counter_cossim(c1, c2), 1)
        self.assertEqual(sim.counter_cossim(c2, c3), 0)
        self.assertEqual(sim.counter_cossim(c3, c4), 0.4)

if __name__ == "__main__":
    unittest.main()

