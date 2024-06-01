package be.ucll.examen.mappers;

public interface Mapper<A, B> {

    B toDto(A a);

    A toEntity(B b);
}
