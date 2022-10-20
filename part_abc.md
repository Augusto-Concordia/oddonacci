//COMP 352 - Assignment #1
//Due Date: October 7th
//Written by: Augusto Mota Pinheiro (40208080)

## a)

### TrinaryOddonacci

```
Algorithm TrinaryOddonacci(n):
Input: n is the Oddonacci number we want.
Output: The nth corresponding Oddonacci number.

if (n = 1 or n = 2 or n = 3) then
    return 1

return TrinaryOddonacci(n - 1) + TrinaryOddonacci(n - 2) + TrinaryOddonacci(n - 3)
```

### LinearOddonacci

```
Algorithm LinearOddonacci(n):
Input: n is the Oddonacci number we want.
Output: An array with the first index being the nth corresponding Oddonacci number, the second being the previous Oddonacci number and the third the one before that.

A ← empty array

if (n = 1) then
    A[0] ← 1
    return

if (n = 2) then
    A[0] ← 1
    A[1] ← 1
    return

if (n = 3) then
    A[0] ← 1
    A[1] ← 1
    A[2] ← 1
    return

A ← LinearOddonacci(n - 1)

i ← A[0]
j ← A[1]

A[0] ← A[0] + A[1] + A[2]
A[1] ← i
A[2] ← j

return A
```

From the results in `OddoOut.txt`, `TrinaryOddonacci` is exponentially slower than any other method which aligns with the theoretical complexity.

We can also see how slow an exponential complexity can get with larger and larger values of `n`, so much so that we never complete the asked 100 Oddonacci numbers and only reach #50.

If we compare with the results ommiting the trinary-recursive method, we can see that other methods are much faster, so much so that the elapsed time is smaller than the margin of error.

There are some measurements (i.e. `LinearOddonacci`), that vary significantly between squential indices (i.e. #60 & #65). It could be the way the JVM calculates time or runs repeated methods or #65 is very well placed to need the least calculations to achieve the result, etc. In brief, I cannot currently explain why that is and further investigation is required.

## b)

The first algorithm (`TrinaryOddonacci`) is of exponential complexity, because for every call the method is being called approximately 2 other times (when _n >= 4_). when _n = 4_: 3 calls, _n = 5_: 5 calls, _n = 6_: 8 calls, _n = 7_: 16 ... basically, time complexity in term of Big-O: `O(2^n)`. It has a space complexity of `O(log n)`.

The second algorithm (`CachedTrinaryOddonacci`) is a modified version of the first that caches the results from previous computations in a hashmap and queries that hashmap when calculating further Oddonacci numbers. I put it there as a comparison and middle ground between the linear recursion method and the trinary one. Despite having same complexity as the first algorithm (at worst, the number of method calls are the same as the first algorithm, for example when _n = 4_), in practice it's much quicker with a larger execution time variation. On a side note, if the cache was persistent across runs, this would be by far the fastest method, resolving to a constant fetch time and a simple sum in the long run. Space complexity would also stay the same as the first algorithm.

Finally, the third algorithm (`LinearOddonacci`) is the fastest and most consistent of the bunch. With a linear complexity, for every single method call, the total number of method calls is _n - 3_; complexity in term of Big-O: `O(n)`. It's much quicker than the first two algorithms because there is no calculation overlap (no double-calculations): every value is computed once, contrary to the first one where, as an example, asking for the 6th number involves calculating the value of 4 twice. However, space complexity is worse than the first two, with it being linear, `O(n)`.

## c)

None of my algorithms use tail recursion, because Java doesn't take advantage of tail-recursive methods, and I program for the language and tools I am using. It would be counter-productive to not do so and a waste of resources in my opinion. Also, tail recursion isn't the most intuitive nor simplest of thinking manners.

However, a tail-recursive algorithm could be designed by going from top to bottom, instead of bottom to top. We would add the initial number of Oddonacci _n_ times until we reach one of the base cases, while swapping the parameters to account for "moving through the sequence". Here's the pseudocode:

```
Algorithm TailOddonacci(n, a, b, c):
Input: n is the Oddonacci number we want, a is the first Oddonacci number, b is the second and c is the third.
Output: The nth corresponding Oddonacci number.

if (n = 1) then
    return a
if (n = 2) then
    return b
if (n = 3) then
    return c

return TailOddonacci(n - 1, b, c, c + b + a)

```

The Java-implemented version is after `LinearOddonacci` in Oddonacci.java.

To my surprise, after some testing, the tail-recursive method actually runs experimentally faster than any other method! It is by far the less intuitive one, but it is the one that requires less computation and number manipulation. Otherwise, it has the same linear time and space Big-O complexity as `LinearOddonacci`.

P.S.: Multiple runs should be done to get the best possible time reading, but Oddonacci #45 took 11 minutes on my machine already, and I don't have infinite time.
