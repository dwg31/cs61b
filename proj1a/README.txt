The removeFirst() and removeLast() methods has a bug.

They return the first or last item of the Deque AFTER the first or last item is removed.

Instead, they should return ...                 BEFORE ...

In proj1b, the bug was fixed.
