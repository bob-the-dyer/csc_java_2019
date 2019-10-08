package ru.compscicenter.java_2019.lesson_04;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _7_SolidTest {

    @Test
    public void srpAndOpenClosedBadSmellAndSolution() {

        class CollectionWrapper<E> {

            Collection<E> collection;

            public CollectionWrapper() {
                this.collection = new ArrayList<>();
            }

            void add(E e) {
                collection.add(e);
            }

            int size() {
                return collection.size();
            }

        }

        class CollectionWrapperAndAnotherCollectionFiller<E> extends CollectionWrapper<E> {

            Collection anotherCollection;

            public CollectionWrapperAndAnotherCollectionFiller(Collection anotherCollection) {
                super();
                this.anotherCollection = anotherCollection;
            }

            void add(E e) {
                collection.add(e);
                anotherCollection.add(e); //bad smell
            }

        }

        List<String> list = new ArrayList<>();
        CollectionWrapperAndAnotherCollectionFiller<String> badSmellWrapper = new CollectionWrapperAndAnotherCollectionFiller<>(list);
        badSmellWrapper.add("ho");
        assertEquals(__, list.size());
        assertEquals(__, badSmellWrapper.size());

        class BetterCollectionWrapper<E> {

            Collection<E> collection;
            List<Consumer<E>> observers = new ArrayList<>();

            public BetterCollectionWrapper() {
                this.collection = new ArrayList<>();
            }

            void add(E e) {
                collection.add(e);
                observers.forEach(c -> c.accept(e));
            }

            int size() {
                return collection.size();
            }

            void addObserver(Consumer<E> observer) {
                observers.add(observer);
            }

        }

        List<String> anotherList = new ArrayList<>();
        BetterCollectionWrapper<String> wrapper = new BetterCollectionWrapper<>();
        wrapper.addObserver(anotherList::add);
        wrapper.addObserver(System.out::println);
        wrapper.add("hey!");
        assertEquals(__, wrapper.size());
        assertEquals(__, anotherList.size());
    }

    @Test
    public void liskovSubstitutionBadSmellAndSolution() {

        class Triangle {

            String draw() {
                return "∆";
            }

        }

        class TransparentTriangle extends Triangle {

            @Override
            String draw() {
                throw new UnsupportedOperationException("transparent triangle shouldn't be drawn!"); //here contract is broken
            }
        }

        List<Triangle> list = List.of(new Triangle(), new TransparentTriangle());
        list.forEach(Triangle::draw);

    }

}
