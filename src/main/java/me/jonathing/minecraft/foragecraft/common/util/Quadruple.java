package me.jonathing.minecraft.foragecraft.common.util;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * I am God.
 * <p>
 * Unused, but in case I ever need it for advanced handling of foraging, I will be ready.
 *
 * @param <A> The first.
 * @param <B> The second.
 * @param <C> The third.
 * @param <D> The fourth.
 * @author Jonathing
 * @since 2.0.0
 */
public class Quadruple<A, B, C, D> implements Comparable<Quadruple<A, B, C, D>>, Serializable
{
    /**
     * Serialization version
     */
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

    public static <A, B, C, D> Quadruple<A, B, C, D> of(A first, B second, C third, D fourth)
    {
        return new Quadruple<>(first, second, third, fourth);
    }

    @Override
    public int compareTo(final Quadruple<A, B, C, D> other)
    {
        return new CompareToBuilder().append(this.first, other.first)
                .append(this.second, other.second)
                .append(this.third, other.third)
                .append(this.fourth, other.fourth).toComparison();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj instanceof Quadruple<?, ?, ?, ?>)
        {
            final Quadruple<?, ?, ?, ?> other = (Quadruple<?, ?, ?, ?>) obj;
            return Objects.equals(this.first, other.first)
                    && Objects.equals(this.second, other.second)
                    && Objects.equals(this.third, other.third)
                    && Objects.equals(this.fourth, other.fourth);
        }
        return false;
    }

    /**
     * Returns a {@link String} containing all of the {@link Object}s in the {@link Quadruple} in the following format:
     *
     * <pre>
     *     "(%s, %s, %s, %s)"
     * </pre>
     *
     * @return The result of {@link #toString(String)} with the above format.
     * @see #toString(String)
     */
    @Override
    public String toString()
    {
        return this.toString("(%s, %s, %s, %s)");
    }

    /**
     * @param format The {@link String} to shove the {@link Quadruple}'s {@link Object}s into.
     * @return A {@link String} containing all of the {@link Quadruple}'s {@link Object}s in a given format.
     * @see #toString()
     */
    public String toString(final String format)
    {
        return String.format(format, this.first.toString(), this.second.toString(), this.third.toString(), this.fourth.toString());
    }

    @Override
    public int hashCode()
    {
        return (this.first == null ? 0 : this.first.hashCode()) ^
                (this.second == null ? 0 : this.second.hashCode()) ^
                (this.third == null ? 0 : this.third.hashCode()) ^
                (this.fourth == null ? 0 : this.fourth.hashCode());
    }

    /**
     * @return {@link #first}
     */
    public A getFirst()
    {
        return this.first;
    }

    /**
     * @return {@link #second}
     */
    public B getSecond()
    {
        return this.second;
    }

    /**
     * @return {@link #third}
     */
    public C getThird()
    {
        return this.third;
    }

    /**
     * @return {@link #fourth}
     */
    public D getFourth()
    {
        return this.fourth;
    }
}
