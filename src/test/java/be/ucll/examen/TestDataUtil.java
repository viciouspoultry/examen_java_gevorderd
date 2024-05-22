package be.ucll.examen;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.domain.entities.Room;

import java.util.ArrayList;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Campus createTestCampusA() {
        Campus campus = new Campus();
        campus.setName("TestNameA");
        campus.setAddress("TestAddressA");
        campus.setParkingCapacity(200);
        campus.setRooms(new ArrayList<>());
        campus.setNumberOfRooms(0);
        return campus;
    }

    public static CampusDto createTestCampusDtoA() {
        CampusDto dto = new CampusDto();
        dto.setName("TestNameA");
        dto.setAddress("TestAddressA");
        dto.setParkingCapacity(200);
        dto.setRooms(new ArrayList<>());
        dto.setNumberOfRooms(0);
        return dto;
    }

    public static Campus createTestCampusB() {
        Campus campus = new Campus();
        campus.setName("TestNameB");
        campus.setAddress("TestAddressB");
        campus.setParkingCapacity(100);
        return campus;
    }

    public static CampusDto createTestCampusDtoB() {
        CampusDto dto = new CampusDto();
        dto.setName("TestNameB");
        dto.setAddress("TestAddressB");
        dto.setParkingCapacity(200);
        dto.setRooms(new ArrayList<>());
        dto.setNumberOfRooms(0);
        return dto;
    }

    public static Campus createTestCampusC() {
        Campus campus = new Campus();
        campus.setName("TestNameC");
        campus.setAddress("TestAddressC");
        campus.setParkingCapacity(50);
        return campus;
    }

    public static Room createTestRoomA(Campus campus) {
        Room room = new Room();
        room.setId(1L);
        room.setName("TestNameA");
        room.setType("TestTypeA");
        room.setCapacity(50);
        room.setFloorNumber(2);
        room.setCampus(campus);
        return room;
    }

    public static Room createTestRoomB(Campus campus) {
        Room room = new Room();
        room.setId(2L);
        room.setName("TestNameB");
        room.setType("TestTypeB");
        room.setCapacity(20);
        room.setFloorNumber(1);
        room.setCampus(campus);
        return room;
    }

    public static Room createTestRoomC(Campus campus) {
        Room room = new Room();
        room.setId(3L);
        room.setName("TestNameC");
        room.setType("TestTypeC");
        room.setCapacity(30);
        room.setFloorNumber(0);
        room.setCampus(campus);
        return room;
    }
}
