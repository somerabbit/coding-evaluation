package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	int id = 0;
	public Optional<Position> hire(Name person, String title) {
		//your code here
		Optional<Position> optionalPosition = findPositionByTitle(root,title);
		if (optionalPosition.isPresent()){
			Position position = optionalPosition.get();
			if (!position.isFilled()){
				Employee employee = new Employee(id++,person);
				position.setEmployee(Optional.of(employee));
				return Optional.of(position);
			}
		}
		return Optional.empty();
	}

	public Optional<Position> findPositionByTitle(Position current, String title){
		if (current.getTitle().equals(title)){
			return Optional.of(current);
		}
		for (Position directReport : current.getDirectReports()){
			Optional<Position> result = findPositionByTitle(directReport,title);
			if (result.isPresent()){
				return result;
			}
		}
		return Optional.empty();
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
