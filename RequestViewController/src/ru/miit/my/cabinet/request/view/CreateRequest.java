package ru.miit.my.cabinet.request.view;

public class CreateRequest {
    public CreateRequest() {
        super();
    }
    
    private class TestType1 { //представление таблицы ввиде класса
        private int idTestType;
        private int idEmployee;
        private int idRequest;
        private int chooseInt;

        public void setIdTestType(int idTestType) {
            this.idTestType = idTestType;
        }

        public int getIdTestType() {
            return idTestType;
        }

        public void setIdEmployee(int idEmployee) {
            this.idEmployee = idEmployee;
        }

        public int getIdEmployee() {
            return idEmployee;
        }

        public void setIdRequest(int idRequest) {
            this.idRequest = idRequest;
        }

        public int getIdRequest() {
            return idRequest;
        }

        public void setChooseInt(int chooseInt) {
            this.chooseInt = chooseInt;
        }

        public int getChooseInt() {
            return chooseInt;
        }
    }
    private class TestType2 { //представление таблицы ввиде класса
        private int idTestType;
        private int idEmployee;
        private int idRequest;
        private int addchar;

        public void setIdTestType(int idTestType) {
            this.idTestType = idTestType;
        }

        public int getIdTestType() {
            return idTestType;
        }

        public void setIdEmployee(int idEmployee) {
            this.idEmployee = idEmployee;
        }

        public int getIdEmployee() {
            return idEmployee;
        }

        public void setIdRequest(int idRequest) {
            this.idRequest = idRequest;
        }

        public int getIdRequest() {
            return idRequest;
        }

        public void setAddchar(int addchar) {
            this.addchar = addchar;
        }

        public int getAddchar() {
            return addchar;
        }
    }
    
}

