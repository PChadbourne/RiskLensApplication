from __future__ import division
import random

def monte_carlo_deck(num, iterations=1000000):
    deck = list(range(52))
    count = 0
    for x in range(iterations):
        random.shuffle(deck)
        match = True
        for y in range(num - 1):
            if (deck[y] // 13) != (deck [y+1] // 13):
                match = False
                break
        if match:
            count += 1
    return (count) / iterations

print(monte_carlo_deck(3))
