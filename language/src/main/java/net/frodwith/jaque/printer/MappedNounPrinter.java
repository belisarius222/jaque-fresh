package net.frodwith.jaque.printer;

import java.util.HashMap;

import net.frodwith.jaque.runtime.HoonMath;

// take a single noun, get a string and a location map
public final class MappedNounPrinter {
  public static final class IndexLength {
    public final int index, length;

    public IndexLength(int index, int length) {
      this.index = index;
      this.length = length;
    }
  }

  public final Writer out;
  public final Map<Object,IndexLength> axisMap = new HashMap<>();

  private MappedNounPrinter(Writer out) {
    this.out = out;
  }

  public static Map<Object,IndexLength> print(Writer out, Object noun) {
    MappedNounPrinter printer = new MappedNounPrinter(out);
    printer.print(noun, 1L, false);
    return printer.axisMap;
  }

  // much simpler to print this recursively, and since it's only used for
  // debug info it's safe for now not to worry about stack overflows
  @TruffleBoundary
  private int print(Object noun, Object axis, int pos, boolean tail) {
    int begin = pos;
    if ( noun instanceof Cell ) {
      Cell c = (Cell) noun;
      if ( !tail ) {
        out.write('[');
        ++pos;
      }
      pos = print(c.head, HoonMath.peg(axis, 2L), pos, false);
      pos = print(c.tail, HoonMath.peg(axis, 3L), pos, true);
      if ( !tail ) {
        out.write(']');
        ++pos;
      }
    }
    else {
      pos += SimpleAtomPrinter.print(out, noun);
    }
    axisMap.put(axis, new IndexLength(begin, pos - begin));
    return pos;
  }
}