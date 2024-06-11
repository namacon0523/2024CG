package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;

@WebServlet("/PokerModel")
public class PokerModel {
    private List<Integer> deckcards; // デッキのカード
    private List<Integer> handcards; // プレイヤーの手札
    private int games; // ゲームの回数
    private int chips; // プレイヤーのチップ数
    private String buttonLabel; // ボタンのラベル
    private String message; // メッセージ
    private boolean gameOver; // ゲームオーバーかどうかのフラグ

    public PokerModel() {
        deckcards = new ArrayList<>();
        handcards = new ArrayList<>(Collections.nCopies(5, 0)); // 手札を5枚の0で初期化
        reset();
    }

    // ゲームをリセットするメソッド
    public void reset() {
        chips = 500;
        games = 0;
        deckcards.clear();
        handcards.clear();
        handcards.addAll(Collections.nCopies(5, 0));  // 初期サイズを設定
        buttonLabel = "";
        message = "";
        gameOver = false;
    }

    // 手札を設定するメソッド
    public void setHandcards(int a, int b, int c, int d, int e) {
        handcards.set(0, a);
        handcards.set(1, b);
        handcards.set(2, c);
        handcards.set(3, d);
        handcards.set(4, e);
    }

    // 次のゲームを開始するメソッド
    public void nextgame() {
        if (gameOver) {
            reset();
        }
        deckcards.clear();
        for (int i = 0; i <= 51; i++) {
            deckcards.add(i);
        }
        Collections.shuffle(deckcards);

        handcards.clear();
        for (int i = 0; i < 5; i++) {
            int n = deckcards.remove(0);
            handcards.add(n);
        }

        message = "交換するカードをチェックしてください";
        buttonLabel = "交換";
        games++;
    }

    // 指定したインデックスのカードを交換するメソッド
    public void change(List<Integer> indexesToChange) {
        for (int index : indexesToChange) {
            if (index >= 0 && index < handcards.size()) {
                int newCard = deckcards.remove(0);
                handcards.set(index, newCard);
            }
        }
        evaluate();
        buttonLabel = "次のゲーム";
    }

    // 手札を評価するメソッド
    public void evaluate() {
        String handName = evaluateHand();
        int chipsChange = 0;

        switch (handName) {
            case "ロイヤルストレートフラッシュ":
                chipsChange = 1000;
                break;
            case "フォーカード":
                chipsChange = 500;
                break;
            case "フルハウス":
                chipsChange = 300;
                break;
            case "フラッシュ":
                chipsChange = 200;
                break;
            case "ストレート":
                chipsChange = 150;
                break;
            case "スリーカード":
                chipsChange = 100;
                break;
            case "ツーペア":
                chipsChange = 50;
                break;
            case "ワンペア":
                chipsChange = 20;
                break;
            case "ハイカード":
                chipsChange = -100;
                break;
        }

        chips += chipsChange;

        if (chips <= 0) {
            message = "ゲームオーバー";
            gameOver = true;
        } else {
            message = handName + " がそろいました " + chipsChange + " チップ";
        }
    }

    // 手札のハンドを評価するメソッド
    public String evaluateHand() {
        List<Integer> values = new ArrayList<>();
        List<Character> suits = new ArrayList<>();
        for (int card : handcards) {
            values.add(card % 13);
            suits.add((char) ((card / 13) + 'A'));
        }

        Collections.sort(values);

        boolean isFlush = suits.stream().distinct().count() == 1;
        boolean isStraight = values.get(4) - values.get(0) == 4 && values.stream().distinct().count() == 5;

        // ロイヤルストレートフラッシュの判定
        if (isFlush && values.equals(List.of(0, 9, 10, 11, 12))) return "ロイヤルストレートフラッシュ";
        if (isFlush) return "フラッシュ";
        if (isStraight) return "ストレート";

        Map<Integer, Long> valueCountMap = values.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long maxCount = valueCountMap.values().stream().max(Long::compareTo).orElse(0L);

        if (maxCount == 4) return "フォーカード";
        if (maxCount == 3 && valueCountMap.size() == 2) return "フルハウス";
        if (maxCount == 3) return "スリーカード";
        if (maxCount == 2 && valueCountMap.size() == 3) return "ツーペア";
        if (maxCount == 2) return "ワンペア";

        return "ハイカード";
    }

    // ゲームの回数を取得するメソッド
    public int getGames() {
        return games;
    }

    // チップの数を取得するメソッド
    public int getChips() {
        return chips;
    }

    // 指定したインデックスの手札を取得するメソッド
    public int getHandcardAt(int i) {
        return handcards.get(i);
    }

    // ボタンのラベルを取得するメソッド
    public String getButtonLabel() {
        if (buttonLabel == null) {
            return "";
        }
        return buttonLabel;
    }

    // メッセージを取得するメソッド
    public String getMessage() {
        return message;
    }
}
