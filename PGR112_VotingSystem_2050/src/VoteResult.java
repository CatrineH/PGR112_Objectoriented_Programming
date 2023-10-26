public record VoteResult(int nomineeId, String name, String studentProgram, int votes, String comments) {
    public String toString() {
        return "VoteResult[nomineeId=%d; name=%s; studentProgram=%s; votes=%d;]".formatted(
                this.nomineeId(), this.name(), this.studentProgram(), this.votes()
        );
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public Integer getId() {
        return nomineeId;
    }

    public String setComment(String comment) {
        return comments;
    }

    public String getComments() {
        return comments;
    }
}
