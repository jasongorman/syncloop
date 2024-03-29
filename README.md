# syncloop
Rough idea for conditional synchronisation in multi-threaded Java

The example is two groups of worker threads - bay loaders and truck loaders - loading parcels into a truck from a warehouse.

The bay loaders load parcels on to a loading bay which has a fixed capacity. truck loaders unload parcels from the bay and load them on to the truck.

When the bay is full, bay loaders wait for space to become available before loading any more parcels.

When the bay is empty, truck loaders wait until more parcels have been loaded on to the bay.

When all the parcels have been loaded on to the bay, the bay loaders stop.

When the truck is fully loaded - all parcels in the manifest have been loaded - then the truck loaders stop.

There can be an arbitrary number of bay and truck loaders (worker threads). In our example, we've used 3 bay loaders and 2 truck loaders, but you can change that for testing.

You can also change the number of parcels, and the capacity of the loading bay. Assume the truck can hold any number of parcels.

# About the SyncLoop micro-library

To enable the conditional synchronisation - wait conditions and exit conditions - I've refactored while loops I originally created into 2 "humble classes" - RunnableLoop and SupplierLoop.

**RunnerLoop** allows me to set up a wait condition with the syntax:

`SyncLoop.execute(action).when(condition, lock);`

It will wait until the condition is true and then execute the action (the condition being the pre-condition for the action) in a synchronised block. While it's waiting, no other threads are blocked.

I can set up an exit condition using:

`SyncLoop.execute(action).until(condition, lock);`

It will repeat the action until the exit condition is satisfied, ending the loop.

using **execute...when** and **execute...until**, I was able to implement my loading example and avoid deadlocks, with decent parallelism.

**SupplierLoop** works like **RunnerLoop**, except that actions can have return values.

The abstract class **SyncLoop** implements static execute methods that can return either a RunnerLoop or SupplierLoop, depending on which kind of function is passed for the action.

So, with a static import, the final syntax can be something like:

`    void load(Parcel parcel){
        execute(() -> parcels.add(parcel))
                .when(() -> !isFull(), this);
    }`
    
Which, hopefully is more intuitively readable. Well, maybe. Sure looks nicer than lots of **while** loops!

# About the custom JUnit assertions

To test my Loading Bay producer-consumer example code, I've knocked up a couple of custom JUnit assertions: **always()** and **eventually()**

**always** persistently applies an assertion in a loop running in its own thread throughout execution of the concurrent thread pool the code being tested runs in (warning, runs slow!) This is an attempt to chec for safety properties during concurrent execution.

**eventually** waits until all threads have finished executing (or have timed out) and then assertions that an outcome was achieved.

I've used JUnitParams to feed a large number of test cases to each unit test, so this is a statistical rather than deterministic approach. 1000 iterations of a test might give us 99.9% confidence the code works. 10,000 might give 99.99%, and so on.


*DISCLAIMER: This is just a prototype. Provided for illustration purposes only, with no warranty*


