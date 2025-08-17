package com.acme.financehub.goals;

import static com.acme.financehub.common.Exceptions.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoalService {
  private final GoalRepository repo;

    public GoalService(GoalRepository repo) {
        this.repo = repo;
    }


    @Transactional public Goal create(Long userId, Goal g){ g.setId(null); g.setUserId(userId); return repo.save(g); }
  public List<Goal> list(Long userId){ return repo.findByUserId(userId); }
  public Goal getOwned(Long userId, Long id){
    Goal g = repo.findById(id).orElseThrow(() -> new NotFoundException("Goal not found"));
    if(!g.getUserId().equals(userId)) throw new ForbiddenException("Not your goal");
    return g;
  }
  @Transactional public Goal update(Long userId, Long id, Goal patch){
    Goal g = getOwned(userId, id);
    if(patch.getTitle()!=null) g.setTitle(patch.getTitle());
    if(patch.getTargetAmount()!=null) g.setTargetAmount(patch.getTargetAmount());
    if(patch.getDeadlineDate()!=null) g.setDeadlineDate(patch.getDeadlineDate());
    if(patch.getCurrentProgress()!=null) g.setCurrentProgress(patch.getCurrentProgress());
    return repo.save(g);
  }
  @Transactional public void delete(Long userId, Long id){ repo.delete(getOwned(userId,id)); }
}
