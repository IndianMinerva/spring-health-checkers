import spock.lang.Specification

class DummyTest extends Specification {

    def "print something"() {
        when:
        true
        then:
        1 == 1
    }
}