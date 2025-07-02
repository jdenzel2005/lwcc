package de.lexware.cc.shared.uuid;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class UUIDV7Generator extends SequenceStyleGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object obj) {
        return UUIDUtils.randomV7();
    }
}
