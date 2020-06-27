package bowling.domain.frame;

import bowling.domain.FrameResults;
import bowling.domain.exceptions.InvalidTryBowlException;
import bowling.domain.exceptions.InvalidTryNextFrameException;
import bowling.domain.frameStatus.FinalFrameStatus;

import java.util.Objects;

public class FinalFrame implements Frame {
    private static int TEN = 10;

    private final int index;
    private final FinalFrameStatus finalFrameStatus;
    private final Frame prevFrame;

    FinalFrame(int index, FinalFrameStatus finalFrameStatus, Frame ninthFrame) {
        this.index = index;
        this.finalFrameStatus = finalFrameStatus;
        this.prevFrame = ninthFrame;
    }

    FinalFrame(NormalFrame ninthFrame, FinalFrameStatus finalFrameStatus) {
        this(TEN, finalFrameStatus, ninthFrame);
    }

    public static FinalFrame bowlFirst(int numberOfHitPin, Frame ninthFrame) {
        return new FinalFrame(TEN, FinalFrameStatus.bowlFirst(numberOfHitPin), ninthFrame);
    }

    private void validateBowl() {
        if (isCompleted()) {
            throw new InvalidTryBowlException("종료된 프레임에는 투구할 수 없습니다.");
        }
    }

    @Override
    public Frame next(int numberOfHitPin) {
        throw new InvalidTryNextFrameException("10 프레임 이후 프레임은 존재하지 않습니다.");
    }

    @Override
    public FinalFrame bowl(int numberOfHitPin) {
        validateBowl();
        return new FinalFrame(TEN, this.finalFrameStatus.bowl(numberOfHitPin), prevFrame);
    }

    @Override
    public boolean isCompleted() {
        return this.finalFrameStatus.isCompleted();
    }

    @Override
    public FrameResults calculateCurrentResults() {
        return this.finalFrameStatus.calculateCurrentResult();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalFrame that = (FinalFrame) o;
        return index == that.index &&
                Objects.equals(finalFrameStatus, that.finalFrameStatus) &&
                Objects.equals(prevFrame, that.prevFrame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, finalFrameStatus, prevFrame);
    }
}