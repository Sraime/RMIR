package exercise4;

/**
 * Interface of a generic computer server.
 *
 * The <code>compute</code> method takes as a parameter the {@link Task} to
 * compute, and returns the value output by the task.
 *
 */
public interface Computer {

  public <T> T compute(Task<T> task);

}
