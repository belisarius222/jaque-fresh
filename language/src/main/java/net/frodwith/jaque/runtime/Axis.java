package net.frodwith.jaque.runtime;

import java.util.Iterator;

public final class Axis implements Iterable<Axis.Fragment> {
  public enum Fragment { HEAD, TAIL }

  public final int length;
  public final Object atom;

  public class Cursor implements Iterator<Fragment> {
    private int n;
    
    public Cursor() {
      this.n = length - 1;
    }

    @Override
    public boolean hasNext() {
      return n >= 0;
    }

    @Override
    public Fragment next() {
      return Atom.getNthBit(atom, n--) ? Fragment.TAIL : Fragment.HEAD;
    }

  }
  
  public Axis(Object atom) {
    this.atom   = atom;
    this.length = HoonMath.met(atom) - 1;
  }

  @Override
  public Iterator<Fragment> iterator() {
    return new Cursor();
  }
}
