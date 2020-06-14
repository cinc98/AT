package model;

public class Car {
		private int year;
		private int km;
		private int power;
		
		private int price;

		
		public Car(int year, int km, int power, int price) {
			super();
			this.year = year;
			this.km = km;
			this.power = power;
			this.price = price;
		}

		public Car() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Car [year=" + year + ", km=" + km + ", power=" + power + ", price=" + price
					+ "]";
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getKm() {
			return km;
		}

		public void setKm(int km) {
			this.km = km;
		}

		public int getPower() {
			return power;
		}

		public void setPower(int power) {
			this.power = power;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}
		
		
}
