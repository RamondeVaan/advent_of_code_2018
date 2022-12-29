package nl.ramondevaan.adventofcode2018.util;

import java.util.List;

public interface Partitioner<T> {
    List<List<T>> partition(List<T> toPartition);
}
