package bowling.view;

import bowling.domain.frame.FinalFrame;
import bowling.domain.frame.Frames;
import bowling.domain.status.*;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FrameView {
    private static final int MAX_FRAME_NO = 10;
    private static final int MIN_FRAME_NO = 1;
    public static final String STRIKE_MARK = "X";
    public static final String SPARE_MARK = "/";
    public static final String TWO_BLANK = "  ";
    public static final String BAR_MARK = "|";

    public static List<String> frameResultTitles() {
        return IntStream.rangeClosed(MIN_FRAME_NO, MAX_FRAME_NO)
                .mapToObj(i -> String.format("%02d", i))
                .collect(toList());
    }

    public static List<String> frameResultContents(Frames frames) {
        List<String> contents = getNormalFramesContents(frames);
        contents.add(getFinalFrameContents(frames));

        for (int i = frames.getCurrentFrameIdx() + 1; i < MAX_FRAME_NO; i++) {
            contents.add(String.format("%7s", BAR_MARK));
        }

        return contents;
    }

    private static List<String> getNormalFramesContents(Frames frames) {
        return frames.getFrameStatus()
                .stream()
                .filter(status -> !Objects.isNull(status))
                .map(status -> { String content = "";
                    if (status instanceof Strike) {
                        content = String.format("%-4s", STRIKE_MARK);
                    }
                    if (status instanceof Spare) {
                        String spareScore = List.of(String.valueOf(status.getCountOfFirst()), SPARE_MARK).stream()
                                .collect(Collectors.joining(BAR_MARK));
                        content = String.format("%-4s", spareScore);
                    }
                    if (status instanceof Miss) {
                        String missScore = List.of(String.valueOf(status.getCountOfFirst()), String.valueOf(status.getCountOfSecond())).stream()
                                .collect(Collectors.joining(BAR_MARK));
                        content = String.format("%-4s", missScore);
                    }
                    if (status instanceof FirstBowl) {
                        content = String.format("%-4s", status.getCountOfFirst() + BAR_MARK);
                    }
                    return String.join(content, TWO_BLANK, BAR_MARK);
                })
                .collect(toList());
    }

    private static String getFinalFrameContents(Frames frames) {
        String finalContent = "";
        if (frames.getCurrentFrame().isFinalFrame()) {
            FinalFrame finalFrame = (FinalFrame) frames.getCurrentFrame();
            finalContent = " " + finalFrame.getStatuses()
                    .stream()
                    .filter(status -> !(status instanceof Ready))
                    .map(status -> {
                        if (status instanceof Strike) {
                            return STRIKE_MARK;
                        }
                        if (status instanceof Spare) {
                            return status.getCountOfFirst() + BAR_MARK + SPARE_MARK;
                        }
                        if (status instanceof Miss) {
                            return status.getCountOfFirst() + BAR_MARK + status.getCountOfSecond();
                        }
                        return String.valueOf(status.getCountOfFirst());
                    }).collect(Collectors.joining(BAR_MARK));

            finalContent = String.format("%-6s", finalContent) + BAR_MARK;
        }
        return finalContent;
    }

}
