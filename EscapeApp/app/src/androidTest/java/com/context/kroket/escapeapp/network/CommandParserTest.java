package com.context.kroket.escapeapp.network;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;

public class CommandParserTest extends CommandParser {

  /**
   * Test for inputStart method.
   */
  @Test
  public void testParseInputStart() {
    String input = "START";

    HashMap<String, String> parsed = CommandParser.parseInput(input);

    assertEquals(parsed.get("command"), "START");
  }

  /**
   * Test for inputSingleParam.
   */
  @Test
  public void testParseInputSingleParam() {
    String input = "REGISTER[Player 1]";

    HashMap<String, String> parsed = CommandParser.parseInput(input);

    assertEquals("REGISTER", parsed.get("command"));
    assertEquals("Player 1", parsed.get("param_0"));
  }

  /**
   * Test parseInputMultiParam.
   */
  @Test
  public void testParseInputMultiParam() {
    String input = "A[B][C][D][E]";

    HashMap<String, String> parsed = CommandParser.parseInput(input);

    assertEquals("A", parsed.get("command"));
    assertEquals("B", parsed.get("param_0"));
    assertEquals("C", parsed.get("param_1"));
    assertEquals("D", parsed.get("param_2"));
    assertEquals("E", parsed.get("param_3"));
  }

  /**
   * Test for parseInputInvalid method.
   */
  @Test
  public void testParseInputInvalid() {
    String input = "A[B][C]][[D[][E]]";

    HashMap<String, String> parsed = CommandParser.parseInput(input);

    assertEquals("A", parsed.get("command"));
    assertEquals("B", parsed.get("param_0"));
    assertEquals("C", parsed.get("param_1"));
    assertEquals("[D[", parsed.get("param_2"));
    assertEquals("E", parsed.get("param_3"));
  }

  /**
   * Test ParseInputEmptyCommand.
   */
  @Test
  public void testParseInputEmptyCommand() {
    String input = "[A]";

    HashMap<String, String> parsed = CommandParser.parseInput(input);

    assertEquals("", parsed.get("command"));
  }

}
