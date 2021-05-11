package bowling.domain.frame;

import bowling.domain.result.TotalResult;
import bowling.domain.turn.FallenPins;
import bowling.error.CannotMakeFrameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FrameTest {

  @ParameterizedTest
  @ValueSource(ints = {0, 11})
  @DisplayName("생성 불가능")
  void invalidCreateTest(int round) {
    assertThatThrownBy(() -> FrameFactory.of(round)).isInstanceOf(CannotMakeFrameException.class);
  }

  @Test
  @DisplayName("전체 진행 테스트")
  void fullGameTest() {
    Frame frame = FrameFactory.of(1);
    frame.bowl(new FallenPins(10))
      .bowl(new FallenPins(10))
      .bowl(new FallenPins(2)).bowl(new FallenPins(8))
      .bowl(new FallenPins(7)).bowl(new FallenPins(1))
      .bowl(new FallenPins(2)).bowl(new FallenPins(4))
      .bowl(new FallenPins(3)).bowl(new FallenPins(6))
      .bowl(new FallenPins(7)).bowl(new FallenPins(2))
      .bowl(new FallenPins(7)).bowl(new FallenPins(1))
      .bowl(new FallenPins(6)).bowl(new FallenPins(0))
      .bowl(new FallenPins(10)).bowl(new FallenPins(1)).bowl(new FallenPins(5));

    TotalResult totalResult = frame.showFullResult();

    System.out.print("|");
    totalResult.frameResults().stream().map(frameResult -> String.format("%5s", frameResult.frameView()) + "|").forEach(System.out::print);
    System.out.println();
    System.out.print("|");
    totalResult.frameResults().stream().map(frameResult -> String.format("%5s", frameResult.score()) + "|").forEach(System.out::print);
  }
}