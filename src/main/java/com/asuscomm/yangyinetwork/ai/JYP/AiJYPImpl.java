package com.asuscomm.yangyinetwork.ai.JYP;


import com.asuscomm.yangyinetwork.ai.algorithms.AlphaBetaForLoop;
import com.asuscomm.yangyinetwork.ai.commons.AiBaseClass;
import com.asuscomm.yangyinetwork.ai.algorithms.domain.Node;
import lombok.extern.slf4j.Slf4j;
import com.asuscomm.yangyinetwork.consts.config.IterativeDeepening;
import com.asuscomm.yangyinetwork.ai.algorithms.AlphaBeta;
import com.asuscomm.yangyinetwork.consts.CONSTS;
import com.asuscomm.yangyinetwork.utils.PrintUtils;


import static com.asuscomm.yangyinetwork.utils.ChooseRandomly.chooseRandomlyInBoard;

/**
 * Created by jaeyoung on 2017. 5. 10..
 */
@Slf4j
public class AiJYPImpl extends AiBaseClass {

    public AiJYPImpl() {
    }

    @Override
    public void run() {
        {
            log.info("AiJYPImpl/run: remainStones=[{}]",this.remainStones);

            if (this.remainStones == 1) {
                setSolution(new int[][]{chooseRandomlyInBoard(board)});
            } else {
                Node rootNode = new Node(board, stoneType);

//                iterativeDeepeningSearch(rootNode);
                int presentDepth = IterativeDeepening.START_DEPTH;
                while(true) {
                    rootNode.extendByEval(presentDepth);
                    log.info("AiJYPImpl/iterativeDeepeningSearch: [{}]", presentDepth);
                    Node bestNode = (Node) AlphaBeta.alphabeta(rootNode,0, -CONSTS.INF, CONSTS.INF, true, presentDepth);
//                    log.info("AiJYPImpl/run: bestNode");
//                    PrintUtils.printBoard(bestNode.getBoard());
                    if(this.terminate) {
                        break;
                    }
                    int[][] stonePointPair = bestNode.getMothersChild().getStonePoints();
                    setSolution(stonePointPair);

                    if(presentDepth >= IterativeDeepening.MAXIMUM_DEPTH) {
                        log.info("AiJYPImpl/run: IterativeDeepening done");
                        done();
                        break;
                    }
                    presentDepth = presentDepth + 1;
                }
            }
        }
    }

    @Override
    public void terminate() {
        log.info("AiJYPImpl/terminate: terminate");
        super.terminate();
        AlphaBetaForLoop.setTerminate(true);
    }
}
