package me.jonathing.minecraft.foragecraft.common.util;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * I am God.
 *
 * @author Jonathing
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class Quadruple<A, B, C, D> implements Comparable<Quadruple<A, B, C, D>>, Serializable
{
    /** Serialization version */
    private static final long serialVersionUID = 1L;
    
    private final A first;
    private final B second;
    private final C third;
    private final D fourth;

    public Quadruple(A first, B second, C third, D fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <A, B, C, D> Quadruple<A, B, C, D> of(A first, B second, C third, D fourth) {
        return new Quadruple<>(first, second, third, fourth);
    }

    @Override
    public int compareTo(final Quadruple<A, B, C, D> other) {
        return new CompareToBuilder().append(getFirst(), other.getFirst())
                .append(getSecond(), other.getSecond())
                .append(getThird(), other.getThird())
                .append(getFourth(), other.getFourth()).toComparison();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Quadruple<?, ?, ?, ?>) {
            final Quadruple<?, ?, ?, ?> other = (Quadruple<?, ?, ?, ?>) obj;
            return Objects.equals(getFirst(), other.getFirst())
                    && Objects.equals(getSecond(), other.getSecond())
                    && Objects.equals(getThird(), other.getThird())
                    && Objects.equals(getFourth(), other.getFourth());
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + getFirst() + "," + getSecond() + "," + getThird() + "," + getFourth() + ")";
    }

    public String toString(final String format) {
        return String.format(format, getFirst(), getSecond(), getThird(), getFourth());
    }

    @Override
    public int hashCode() {
        return (getFirst() == null ? 0 : getFirst().hashCode()) ^
                (getSecond() == null ? 0 : getSecond().hashCode()) ^
                (getThird() == null ? 0 : getThird().hashCode()) ^
                (getFourth() == null ? 0 : getFourth().hashCode());
    }

    public A getFirst()
    {
        return this.first;
    }

    public B getSecond()
    {
        return this.second;
    }

    public C getThird()
    {
        return this.third;
    }

    public D getFourth()
    {
        return this.fourth;
    }
}
